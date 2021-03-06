package za.ac.nwu.ac.domain.persistence;

import lombok.Data;
import za.ac.nwu.ac.domain.exception.AlbumNotFoundException;
import za.ac.nwu.ac.domain.persistence.album.Album;
import za.ac.nwu.ac.domain.persistence.album.SharedAlbum;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
//import lombok;
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

    private String name;

    private String surname;

    private String encodedPassword;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)//, orphanRemoval = true)
    private List<SharedAlbum> albums;

    @OneToOne(cascade = {CascadeType.ALL})
    private Album ownedPhotosAlbum;

    @OneToOne(cascade = {CascadeType.ALL})
    private Album sharedPhotosAlbum;

    public UserAccount() {
    }

    public UserAccount(String email, String encodedPassword) {
        generateAllPhotoSharableLinks();
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.albums  = new ArrayList<>();
        this.ownedPhotosAlbum = new Album(getName()+" "+getSurname() + " Owned Album" );
        this.sharedPhotosAlbum = new Album(getName()+" "+getSurname() + " Shared Album" );
    }

    public UserAccount(String email, String name, String surname, String encodedPassword) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.encodedPassword = encodedPassword;
        this.albums  = new ArrayList<>();
        this.ownedPhotosAlbum = new Album(getName()+" "+getSurname() + " Owned Album" );
        this.sharedPhotosAlbum = new Album(getName()+" "+getSurname() + " Shared Album" );
    }

    public UserAccount(String email, String encodedPassword, List<SharedAlbum> albums, Album ownedPhotosAlbum, Album sharedPhotosAlbum) {
        //generateAllPhotoSharableLinks();
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.albums = albums;
        this.ownedPhotosAlbum = ownedPhotosAlbum;
        this.sharedPhotosAlbum = sharedPhotosAlbum;
    }

    public void createAlbum(String albumName) {
        this.albums.add(new SharedAlbum(albumName,this));
    }

    public void addPhotoToAlbum(String albumName, Photo photo){
        findAlbumByName(albumName).addPhotoToAlbum(photo);
    }

    public Album findAlbumByName(String name){
        for (Album album : getAlbums()){
            if (album.getAlbumName().equals(name))
                return album;
        }
        throw new AlbumNotFoundException("Album was not found");
    }

    public void addSharedPhoto(Photo photo) {
        this.sharedPhotosAlbum.addPhotoToAlbum(photo);
    }

    public void removeSharedPhoto(Photo photo){
        this.sharedPhotosAlbum.removePhotoFromAlbum(photo);
    }

    public void addOwnedPhoto(Photo photo) {
        this.ownedPhotosAlbum.addPhotoToAlbum(photo);
    }

    public Photo findMostRecentlyAddedOwnedPhoto(){
        try {
            return this.ownedPhotosAlbum.getPhotos().get(ownedPhotosAlbum.getPhotos().size()-1);
        } catch (Exception e){
            return null;
        }
    }

    public void addAlbumToAlbums(SharedAlbum album){
        for (Photo photo: album.getPhotos()) {
            if(!sharedPhotosAlbum.getPhotos().contains(photo)){
                sharedPhotosAlbum.addPhotoToAlbum(photo);
            }
        }
        albums.add(album);
    }

    public String generatePhotoName(){
        try {
            return getUserAccountId() + getSurname() + getName() + findMostRecentlyAddedOwnedPhoto().getPhotoId();
        } catch (Exception e){
            return getUserAccountId() + getSurname() + getName() + 0;
        }
    }

    public Album generateAllPhotoSharableLinks(){
        for (Photo photo: getOwnedPhotosAlbum().getPhotos()) {
            photo.setSharablePhotoLink(photo.generateSharablePhotoLink());
        }
        return getOwnedPhotosAlbum();
    }

    public void generateAllAlbumSharableLinks(){
        for (SharedAlbum album: albums) {
            album.setSharableAlbumLink(album.generateSharableAlbumLink());
        }
    }
}
