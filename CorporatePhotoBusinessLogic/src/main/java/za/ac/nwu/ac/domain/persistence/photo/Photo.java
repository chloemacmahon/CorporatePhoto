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

    //@Column(unique = true)
    private String sharablePhotoLink;

    @OneToOne(cascade = {CascadeType.ALL})
    private PhotoMetaData photoMetaData;

    public Photo() {

    }

    public Photo(PhotoMetaData photoMetaData) {
        this.photoMetaData = photoMetaData;
        setSharablePhotoLink(generateSharablePhotoLink());
    }

    public Photo(String photoLink, PhotoMetaData photoMetaData) {
        this.photoLink = photoLink;
        this.photoMetaData = photoMetaData;
        setSharablePhotoLink(generateSharablePhotoLink());
    }

    public Photo(String photoLink, String sharablePhotoLink, PhotoMetaData photoMetaData) {
        this.photoLink = photoLink;
        this.sharablePhotoLink = sharablePhotoLink;
        this.photoMetaData = photoMetaData;
    }

    public void setSharablePhotoLink(String sharablePhotoLink) {
        this.sharablePhotoLink = generateSharablePhotoLink();
    }

    public void setSharablePhotoLink() {
        this.sharablePhotoLink = generateSharablePhotoLink();
    }

    public String generateSharablePhotoLink(){
        if (sharablePhotoLink == null) {
            return "sharable/" + getPhotoMetaData().getOwner().getSurname() + getPhotoMetaData().getOwner().getName() + Math.random();
        }else
            return "sharable/" + getPhotoMetaData().getOwner().getSurname() + getPhotoMetaData().getOwner().getName() +photoId;
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

    /*@Override
    public String toString() {
        return "Photo{" +
                "photoId=" + photoId +
                ", photoLink='" + photoLink + '\'' +
                ", photoMetaData=" + photoMetaData +
                '}';
    }*/
}
