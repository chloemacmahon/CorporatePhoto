package za.ac.nwu.ac.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;

import java.io.IOException;
import java.util.Objects;

@Data
@Component
public class PhotoDto {
    private String photoLink;
    private PhotoMetaData photoMetaData;

    public PhotoDto() {
    }

    public PhotoDto(PhotoMetaData photoMetaData) {
        this.photoMetaData = photoMetaData;
    }

    public PhotoDto(String photoLink, PhotoMetaData photoMetaData) {
        this.photoLink = photoLink;
        this.photoMetaData = photoMetaData;
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

    //Functions/Methods
//    public String FindPhotoLinkByPhotoId(Long photoId){
//
//        Photo photo = new Photo();
//        if(photo.getPhotoId().equals(photoId))
//            return photo.getPhotoLink();
//        else
//            return "Photo ont found";//TODO: do proper exception handinling here XD
//
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoDto photoDto = (PhotoDto) o;
        return Objects.equals(photoLink, photoDto.photoLink) &&
                Objects.equals(photoMetaData, photoDto.photoMetaData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoLink, photoMetaData);
    }

    @Override
    public String toString() {
        return "PhotoDto{" +
                "photoLink='" + photoLink + '\'' +
                ", photoMetaData=" + photoMetaData +
                '}';
    }
}


