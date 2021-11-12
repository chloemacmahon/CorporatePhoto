package za.ac.nwu.ac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.ac.nwu.ac.domain.persistence.photo.Tag;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    @Query(value = "SELECT tagId FROM Tag WHERE tagName = :tagName", nativeQuery = true)
    Long findTagIdByTagNameLong(@Param("tagName") String tagName);

    @Query(value = "SELECT tagId FROM Tag WHERE tagName = :tagName", nativeQuery = true)
    List<Tag> findTagIdByTagName(@Param("tagName") String tagName);
}
