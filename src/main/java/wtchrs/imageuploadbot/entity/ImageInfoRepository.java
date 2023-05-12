package wtchrs.imageuploadbot.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, ImageInfo.ImageInfoId> {
}
