package za.ac.nwu.ac.domain.persistence.photo;

import lombok.Data;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.UserAccount;

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

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private UserAccount owner;

    public PhotoMetaData() {
    }

    public PhotoMetaData(Date dateCaptured, UserAccount owner) {
        this.dateCaptured = dateCaptured;
        this.owner = owner;
    }
}
