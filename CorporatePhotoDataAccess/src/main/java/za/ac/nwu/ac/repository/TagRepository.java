package za.ac.nwu.ac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.nwu.ac.domain.persistence.photo.Tag;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
