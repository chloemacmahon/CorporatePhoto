package za.ac.nwu.ac.logic.service;

import za.ac.nwu.ac.domain.dto.PhotoDto;
import za.ac.nwu.ac.domain.persistence.photo.Photo;

public interface PhotoServices {
    //PhotoServiceImple -> PhotoReporsitory

    void UploadImage(String userID, String path);
    void DownloadImage(Long blobName);
}
