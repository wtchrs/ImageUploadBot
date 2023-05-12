package wtchrs.imageuploadbot.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@IdClass(ImageInfo.ImageInfoId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class ImageInfo {

    @Id
    @Column(updatable = false, nullable = false)
    private Long memberId;

    @Id
    @Column(updatable = false, nullable = false)
    private String imageName;

    @Column(nullable = false)
    private String savedName;

    @Column(length = 4, nullable = false)
    private String imageType;

    private int imageSize;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime uploadedDate;

    public ImageInfo(Long memberId, String imageName, String savedName, String imageType, int imageSize) {
        this.memberId = memberId;
        this.imageName = imageName;
        this.savedName = savedName;
        this.imageType = imageType;
        this.imageSize = imageSize;
    }

    public static ImageInfoId getId(Long memberId, String imageName) {
        return new ImageInfoId(memberId, imageName);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class ImageInfoId implements Serializable {
        private Long memberId;
        private String imageName;
    }
}
