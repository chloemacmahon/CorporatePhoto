package dto.image;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Component
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long imageId;

    @OneToOne
    private PhotoMetaData photoMetaData;

    public Photo() {
    }

    public Photo(PhotoMetaData photoMetaData) {
        this.photoMetaData = photoMetaData;
    }
}
