package za.ac.nwu.ac.domain.dto;


import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.photo.Photo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class AlbumDto {

    @NotEmpty
    private String albumName;

    private List<Photo> photos;

    public AlbumDto() {
    }

    public AlbumDto(String albumName) {
        this.albumName = albumName;
        this.photos = new ArrayList<>();
    }

    public AlbumDto(String albumName, List<Photo> photos) {
        this.albumName = albumName;
        this.photos = photos;
    }
}
