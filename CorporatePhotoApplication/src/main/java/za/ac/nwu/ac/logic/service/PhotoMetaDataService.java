package za.ac.nwu.ac.logic.service;

import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import za.ac.nwu.ac.domain.persistence.photo.Tag;

import java.time.LocalDate;
import java.util.List;

public interface PhotoMetaDataService {

    void readPhotoMetaData(String photoPath);

    public String readPhotoMetaDataGeolocation(Long photoId, Long metaDataId);

    PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags, String geolocation);

    Tag createTag(String tagName, String tagDescription);

    List<Tag> viewAllTags();

    void deletePhotoMetaDataFromDatabase(Long photoId, Long metaDataId);

    void updatePhotoMetaDataGeolocation(Long photoMetaDataId, String geolocation);

    void removePhotoMetaDataGeolocation(Long photoMetaDataId);

    void updatePhotoTag (Long photoMetaDataId, Long tagId, String newTagName);

    void updatePhotoTag (Long tagId, String newTagName);

    Long findPhotoMetaDataTagByTagName(String tagName);

    void removePhotoTagFromPhotoMetaData (Long photoMetaDataId, Long tagId);

    void deletePhotoTagFromDatabase (Long photoMetaDataId, Long tagId);

    Long searchPhotoByDateCaptured(LocalDate dateCaptured, Long owner);

    Long searchPhotoByGeolocation(String geolocation, Long owner);

    Long searchPhotoByTagName(String tagName);

    public Long searchPhotoByTag(Long tagId, Long owner);

    PhotoMetaData findPhotoMetaDataIdByPhotoId(Long photoId);
}
