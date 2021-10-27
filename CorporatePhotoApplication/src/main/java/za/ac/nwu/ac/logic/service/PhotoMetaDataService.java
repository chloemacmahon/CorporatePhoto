package za.ac.nwu.ac.logic.service;

import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import za.ac.nwu.ac.domain.persistence.photo.Tag;

import java.time.LocalDate;
import java.util.List;

public interface PhotoMetaDataService {

    void readPhotoMetaData(String photoPath);

    PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner);

    PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags);

    PhotoMetaData createPhotoMetaData(LocalDate dateCaptured, UserAccount owner, List<Tag> tags, String geolocation);

    Tag createTag(String tagName, String tagDescription);

    PhotoMetaData viewPhotoMetaData(Long metaDataId);

    void deletePhotoMetaDataFromDatabase(Long photoId, Long metaDataId);

    //void updatePhotoMetaDataGeolocation(Long metaDataID, String geolocation);
}
