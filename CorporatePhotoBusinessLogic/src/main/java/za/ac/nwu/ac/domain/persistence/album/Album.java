package za.ac.nwu.ac.domain.persistence.album;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import za.ac.nwu.ac.domain.exception.PhotoNotInAlbumException;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import lombok.Data;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.photo.Tag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Component
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Long albumId;

    protected String albumName;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    protected List<Photo> photos;

    public Album() {
    }

    public Album(String albumName) {
        this.albumName = albumName;
        this.photos = new ArrayList<>();
    }

    public Album(String albumName, List<Photo> photos) {
        this.albumName = albumName;
        this.photos = photos;
    }

    public void addPhotoToAlbum(Photo photo){
        this.getPhotos().add(photo);
    }

    public void removePhotoFromAlbum(Photo photo){
        try {
            this.photos.remove(photo);
        } catch (Exception e){
            throw new PhotoNotInAlbumException();
        }
    }

    public void generateAllPhotoSharableLinks(){
        for (Photo photo: getPhotos()) {
            photo.setSharablePhotoLink(photo.generateSharablePhotoLink());
        }
    }
}
