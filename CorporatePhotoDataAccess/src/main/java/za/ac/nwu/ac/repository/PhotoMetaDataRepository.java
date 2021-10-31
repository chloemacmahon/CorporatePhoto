package za.ac.nwu.ac.repository;

import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoMetaDataRepository extends JpaRepository<PhotoMetaData, Long> {

    //PhotoMetaData findTagsByMetaDataId (Long metaDataId);
}
