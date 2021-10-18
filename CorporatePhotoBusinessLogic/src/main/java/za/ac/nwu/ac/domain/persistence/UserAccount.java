package za.ac.nwu.ac.domain.persistence;

import za.ac.nwu.ac.domain.exception.AlbumNotFoundException;
import za.ac.nwu.ac.domain.persistence.album.Album;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Component
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userAccountId;

    @Column(unique = true)
    private String email;

    private String encodedPassword;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<Album> albums;

    @OneToOne(cascade = {CascadeType.ALL})
    private Album ownedPhotosAlbum;

    @OneToOne(cascade = {CascadeType.ALL})
    private Album sharedPhotosAlbum;

    public UserAccount() {
    }

    public UserAccount(String email, String encodedPassword) {
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.albums  = new ArrayList<>();
        this.ownedPhotosAlbum = new Album();
        this.sharedPhotosAlbum = new Album();
    }

    public UserAccount(String email, String encodedPassword, List<Album> albums, Album ownedPhotosAlbum, Album sharedPhotosAlbum) {
        this.email = email;
        this.encodedPassword = encodedPassword;
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
