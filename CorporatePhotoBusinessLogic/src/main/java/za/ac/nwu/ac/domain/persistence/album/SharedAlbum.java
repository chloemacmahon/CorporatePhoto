package za.ac.nwu.ac.domain.persistence.album;

import lombok.Data;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.photo.Photo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Component
public class SharedAlbum extends Album {

    @OneToOne(cascade = {CascadeType.PERSIST})
    private UserAccount owner;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<UserAccount> accessAccounts;

    @Column(unique = true)
    private String sharableAlbumLink;

    public SharedAlbum() {
    }

    public SharedAlbum(UserAccount owner) {
        this.owner = owner;
        this.accessAccounts = new ArrayList<>();
    }

    public SharedAlbum(String albumName, UserAccount owner) {
        super(albumName);
        this.owner = owner;
        this.accessAccounts = new ArrayList<>();
    }

    public SharedAlbum(String albumName, List<Photo> photos, UserAccount owner) {
        super(albumName, photos);
        this.owner = owner;
        this.accessAccounts = new ArrayList<>();
    }

    public SharedAlbum(UserAccount owner, List<UserAccount> accessAccounts) {
        this.owner = owner;
        this.accessAccounts = accessAccounts;
    }

    public SharedAlbum(String albumName, UserAccount owner, List<UserAccount> accessAccounts) {
        super(albumName);
        this.owner = owner;
        this.accessAccounts = accessAccounts;
    }

    public SharedAlbum(String albumName, List<Photo> photos, UserAccount owner, List<UserAccount> accessAccounts) {
        super(albumName, photos);
        this.owner = owner;
        this.accessAccounts = accessAccounts;
    }

    public SharedAlbum(UserAccount owner, List<UserAccount> accessAccounts, String sharableAlbumLink) {
        this.owner = owner;
        this.accessAccounts = accessAccounts;
        this.sharableAlbumLink = sharableAlbumLink;
    }

    public SharedAlbum(String albumName, UserAccount owner, List<UserAccount> accessAccounts, String sharableAlbumLink) {
        super(albumName);
        this.owner = owner;
        this.accessAccounts = accessAccounts;
        this.sharableAlbumLink = sharableAlbumLink;
    }

    public SharedAlbum(String albumName, List<Photo> photos, UserAccount owner, List<UserAccount> accessAccounts, String sharableAlbumLink) {
        super(albumName, photos);
        this.owner = owner;
        this.accessAccounts = accessAccounts;
        this.sharableAlbumLink = sharableAlbumLink;
    }

    public void setSharableAlbumLink() {
        this.sharableAlbumLink = generateSharableAlbumLink();
    }

    public String generateSharableAlbumLink(){
        if (sharableAlbumLink == null && this.albumId == null) {
            return "sharable-album/" + this.albumName + Math.random();
        }else
            return "sharable/" + this.albumName + albumId;
    }

    public void addAccessAccount(UserAccount userAccount) {
        accessAccounts.add(userAccount);
    }
}

