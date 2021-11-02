package za.ac.nwu.ac.logic.service;

import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import za.ac.nwu.ac.domain.persistence.photo.Tag;

import java.time.LocalDate;
import java.util.List;

public interface PhotoMetaDataService {

    void readPhotoMetaData(String photoPath);

    public String readPhotoMetaDataGeolocation(Long photoId, Long metaDataId);

    PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags);

    Tag createTag(String tagName, String tagDescription);

    List<Tag> viewAllTags();

    void deletePhotoMetaDataFromDatabase(Long photoId, Long metaDataId);

    void updatePhotoMetaDataGeolocation(Long photoMetaDataId, String geolocation);
}
