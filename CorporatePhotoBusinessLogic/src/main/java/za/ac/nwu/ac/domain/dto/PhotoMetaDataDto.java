package za.ac.nwu.ac.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.photo.Tag;

import java.time.LocalDate;
import java.util.List;

@Data
@Component
public class PhotoMetaDataDto {

    private LocalDate dateCaptured;

    private UserAccount owner;

    private List<Tag> tags;

    private String geolocation;

    public PhotoMetaDataDto(LocalDate dateCaptured, UserAccount owner) {
        this.dateCaptured = dateCaptured;
        this.owner = owner;
    }

    public PhotoMetaDataDto(LocalDate dateCaptured, UserAccount owner, List<Tag> tags) {
        this.dateCaptured = dateCaptured;
        this.owner = owner;
        this.tags = tags;
    }

    public PhotoMetaDataDto(LocalDate dateCaptured, UserAccount owner, List<Tag> tags, String geolocation) {
        this.dateCaptured = dateCaptured;
        this.owner = owner;
        this.tags = tags;
        this.geolocation = geolocation;
    }
}
