package za.ac.nwu.ac.domain.persistence.photo;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.UserAccount;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Component
public class PhotoMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long metaDataId;

    private LocalDate dateCaptured;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private UserAccount owner;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Tag> tags;

    private String geolocation;

    public PhotoMetaData() {
    }

    public PhotoMetaData(LocalDate dateCaptured, UserAccount owner) {
        this.dateCaptured = dateCaptured;
        this.owner = owner;
    }

    public PhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags) {
        this.dateCaptured = dateCaptured;
        this.owner = owner;
        this.tags = tags;
    }

    public PhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags, String geolocation) {
        this.dateCaptured = dateCaptured;
        this.owner = owner;
        this.tags = tags;
        this.geolocation = geolocation;
    }

    public void addTagToPhotoMetaData(Tag tag){
        this.tags.add(tag);
    }
}
