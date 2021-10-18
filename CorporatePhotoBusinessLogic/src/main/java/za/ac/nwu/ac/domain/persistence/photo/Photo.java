package za.ac.nwu.ac.domain.persistence.photo;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Component
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long photoId;

    @Column(unique = true)
    private String photoLink;

    @OneToOne(cascade = {CascadeType.ALL})
    private PhotoMetaData photoMetaData;

    public Photo() {
    }

    public Photo(PhotoMetaData photoMetaData) {
        this.photoMetaData = photoMetaData;
    }
}
