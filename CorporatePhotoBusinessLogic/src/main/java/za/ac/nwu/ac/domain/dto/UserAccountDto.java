package za.ac.nwu.ac.domain.dto;


import lombok.Data;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.album.Album;
import za.ac.nwu.ac.domain.persistence.photo.Photo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Component
public class UserAccountDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private List<Album> albums;

    private Album ownedPhotosAlbum;

    private Album sharedPhotosAlbum;

    public UserAccountDto() {
    }

    public UserAccountDto(@NotEmpty String email, @NotEmpty String password) {
        this.email = email;
        this.password = password;
        this.ownedPhotosAlbum = new Album();
        this.sharedPhotosAlbum = new Album();
    }

    public UserAccountDto(@NotEmpty String email, @NotEmpty String password, List<Album> albums, Album ownedPhotosAlbum, Album sharedPhotosAlbum) {
        this.email = email;
        this.password = password;
        this.albums = albums;
        this.ownedPhotosAlbum = ownedPhotosAlbum;
        this.sharedPhotosAlbum = sharedPhotosAlbum;
    }

    /*public void addPhotoToAlbum(String albumName, Photo photo){
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
    }*/
}
