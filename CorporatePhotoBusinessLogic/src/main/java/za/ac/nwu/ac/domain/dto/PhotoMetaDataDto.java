package za.ac.nwu.ac.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.UserAccount;

import javax.persistence.*;
import java.util.Date;

@Data
@Component
public class PhotoMetaDataDto {

    private Date dateCaptured;

    private UserAccount owner;

    public PhotoMetaDataDto() {
    }

    public PhotoMetaDataDto(Date dateCaptured, UserAccount owner) {
        this.dateCaptured = dateCaptured;
        this.owner = owner;
    }
}
