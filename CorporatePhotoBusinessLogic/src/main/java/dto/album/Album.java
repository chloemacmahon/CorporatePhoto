package dto.album;


import dto.image.Photo;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Component
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long albumId;

    private String albumName;

    @ManyToMany
    private List<Photo> photos;

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
}
