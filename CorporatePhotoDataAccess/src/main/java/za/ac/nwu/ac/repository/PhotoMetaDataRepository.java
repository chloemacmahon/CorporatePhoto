package za.ac.nwu.ac.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhotoMetaDataRepository extends JpaRepository<PhotoMetaData, Long> {

    //PhotoMetaData findTagsByMetaDataId (Long metaDataId);
    @Query(value = "SELECT metaDataId FROM PhotoMetaData WHERE dateCaptured = :dateCaptured AND owner = :owner", nativeQuery = true)
    List<Photo> findPhotoMetaDataIdByDateCaptured(@Param("dateCaptured") LocalDate dateCaptured, @Param("owner") Long owner);

    @Query(value = "SELECT metaDataId FROM PhotoMetaData WHERE geolocation = :geolocation AND owner = :owner", nativeQuery = true)
    List<Photo> findPhotoMetaDataIdByGeolocation(@Param("geolocation") String geolocation, @Param("owner") Long owner);

    @Query(value = "SELECT photoId FROM Photo WHERE photoMetaData = :photoMetaData", nativeQuery = true)
    List<Photo> findPhotoIdByPhotoMetaDataId(@Param("photoMetaData") List<Photo> photoMetaData);
}
