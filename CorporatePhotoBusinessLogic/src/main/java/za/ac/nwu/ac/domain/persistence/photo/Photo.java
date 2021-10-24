package za.ac.nwu.ac.domain.persistence.photo;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Component
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long photoId;

    @Column(unique = true)
    private String photoLink;

    @OneToOne(cascade = {CascadeType.ALL})
    private PhotoMetaData photoMetaData;

    public Photo() {
    }

    public Photo(PhotoMetaData photoMetaData) {
        this.photoMetaData = photoMetaData;
    }

    public Photo(String photoLink, PhotoMetaData photoMetaData) {
    }


    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public PhotoMetaData getPhotoMetaData() {
        return photoMetaData;
    }

    public void setPhotoMetaData(PhotoMetaData photoMetaData) {
        this.photoMetaData = photoMetaData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(photoId, photo.photoId) &&
                Objects.equals(photoLink, photo.photoLink) &&
                Objects.equals(photoMetaData, photo.photoMetaData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoId, photoLink, photoMetaData);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "photoId=" + photoId +
                ", photoLink='" + photoLink + '\'' +
                ", photoMetaData=" + photoMetaData +
                '}';
    }
}
