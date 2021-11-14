package za.ac.nwu.ac.repository;

import za.ac.nwu.ac.domain.persistence.photo.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Photo findPhotoBySharablePhotoLink(String sharablePhotoLink);
}
