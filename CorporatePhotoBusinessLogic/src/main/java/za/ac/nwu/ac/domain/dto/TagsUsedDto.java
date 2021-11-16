package za.ac.nwu.ac.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import za.ac.nwu.ac.domain.persistence.photo.Tag;

import java.util.List;

@Data
@Component
public class TagsUsedDto {
    private List<Tag> tags;

    public TagsUsedDto() {
    }

    public void addTag(Tag tag){
        tags.add(tag);
    }
}
