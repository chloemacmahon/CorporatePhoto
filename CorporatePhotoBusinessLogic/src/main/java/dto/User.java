package dto;

import dto.album.Album;
import dto.image.Photo;
import exception.AlbumNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Component
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @OneToMany
    private List<Album> albums;

    @OneToOne
    private Album ownedPhotosAlbum;

    @OneToOne
    private Album sharedPhotosAlbum;

    public User() {
    }

    public User(@NotEmpty String email, @NotEmpty String password) {
        this.email = email;
        this.password = password;
        this.ownedPhotosAlbum = new Album();
        this.sharedPhotosAlbum = new Album();
    }

    public User(@NotEmpty String email, @NotEmpty String password, List<Album> albums, Album ownedPhotosAlbum, Album sharedPhotosAlbum) {
        this.email = email;
        this.password = password;
        this.albums = albums;
        this.ownedPhotosAlbum = ownedPhotosAlbum;
        this.sharedPhotosAlbum = sharedPhotosAlbum;
    }

    public void addPhotoToAlbum(String albumName, Photo photo){
        findAlbumByName(albumName).addPhotoToAlbum(photo);
    }

    public Album findAlbumByName(String name){
        for (Album album : getAlbums()){
            if (album.getAlbumName().equals(name))
                return album;
        }
        throw new AlbumNotFoundException();
    }

    public void AddSharedImage(Photo photo) {
        this.sharedPhotosAlbum.addPhotoToAlbum(photo);
    }

    public void AddOwnedImage(Photo photo) {
        this.ownedPhotosAlbum.addPhotoToAlbum(photo);
    }
}
