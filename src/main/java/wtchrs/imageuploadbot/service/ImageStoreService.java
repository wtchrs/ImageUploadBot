package wtchrs.imageuploadbot.service;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wtchrs.imageuploadbot.entity.ImageInfo;
import wtchrs.imageuploadbot.entity.ImageInfoRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageStoreService {

    @Value("${image.dir}")
    private String imageDir;

    private final ImageInfoRepository imageInfoRepository;

    public String getFullPath(String filename) {
        return imageDir + "/" + filename;
    }

    @Transactional
    public ImageInfo saveImage(Long memberId, String name, Message.Attachment image) throws IOException {
        ImageInfo.ImageInfoId id = new ImageInfo.ImageInfoId(memberId, name);
        String fileExtension = image.getFileExtension();
        int size = image.getSize();

        if (imageInfoRepository.existsById(id)) throw new IllegalStateException("Already exists image name.");

        String uuidFilename = UUID.randomUUID() + "." + fileExtension;
        image.getProxy().downloadToFile(new File(getFullPath(uuidFilename)));
        return imageInfoRepository.save(new ImageInfo(memberId, name, uuidFilename, fileExtension, size));
    }

    @Transactional(readOnly = true)
    public File getImageFile(Long memberId, String name) {
        ImageInfo image = imageInfoRepository
                .findById(ImageInfo.getId(memberId, name))
                .orElseThrow(() -> new IllegalStateException("Not exists image."));

        return new File(getFullPath(image.getSavedName()));
    }

    // TODO: delete
}
