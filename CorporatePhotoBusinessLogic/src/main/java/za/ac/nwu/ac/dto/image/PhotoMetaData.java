package za.ac.nwu.ac.dto.image;

import za.ac.nwu.ac.dto.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Component
public class PhotoMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long metaDataId;

    private Date dateCaptured;

    @ManyToOne
    private User owner;

    public PhotoMetaData() {
    }

    public PhotoMetaData(Date dateCaptured, User owner) {
        this.dateCaptured = dateCaptured;
        this.owner = owner;
    }
}
