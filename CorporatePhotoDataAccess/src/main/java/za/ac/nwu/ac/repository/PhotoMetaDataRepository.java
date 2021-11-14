package za.ac.nwu.ac.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.nwu.ac.domain.persistence.photo.Tag;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhotoMetaDataRepository extends JpaRepository<PhotoMetaData, Long> {

    //PhotoMetaData findTagsByMetaDataId (Long metaDataId);
    @Query(value = "SELECT meta_data_id FROM Photo_Meta_Data WHERE dateCaptured = :dateCaptured AND owner_user_account_id = :owner", nativeQuery = true)
    Long findPhotoMetaDataIdByDateCaptured(@Param("dateCaptured") LocalDate dateCaptured, @Param("owner") Long owner);

    @Query(value = "SELECT meta_data_id FROM Photo_Meta_Data WHERE geolocation = :geolocation AND owner_user_account_id = :owner", nativeQuery = true)
    Long findPhotoMetaDataIdByGeolocation(@Param("geolocation") String geolocation, @Param("owner") Long owner);

    @Query(value = "SELECT photo_meta_data_meta_data_id FROM Photo_Meta_Data_Tags WHERE tags_tag_Id = :tagId", nativeQuery = true)
    Long findPhotoMetaDataIdByTagId(@Param("tagId") Long tagId);

    @Query(value = "SELECT photo_Id FROM Photo WHERE photo_meta_data_meta_data_id = :photoMetaData", nativeQuery = true)
    Long findPhotoIdByPhotoMetaDataId(@Param("photoMetaData") Long photoMetaData);

}
