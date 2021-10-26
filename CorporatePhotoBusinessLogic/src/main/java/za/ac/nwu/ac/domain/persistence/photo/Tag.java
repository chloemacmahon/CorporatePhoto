package za.ac.nwu.ac.domain.persistence.photo;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Data
@Entity
@Component
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tagId;

    @Column(unique = true)
    private String tagName;

    private String tagDescription;

    public Tag() {
    }

    public Tag(String tagName, String tagDescription) {
        this.tagName = tagName;
        this.tagDescription = tagDescription;
    }
}
