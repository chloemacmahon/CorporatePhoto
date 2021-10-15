package repository;

import dto.image.PhotoMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoMetaDataRepository extends JpaRepository<PhotoMetaData, Long> {
}
