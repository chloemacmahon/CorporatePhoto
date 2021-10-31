package za.ac.nwu.ac.logic.service;

import org.springframework.web.multipart.MultipartFile;
import za.ac.nwu.ac.domain.dto.PhotoDto;
import za.ac.nwu.ac.domain.persistence.photo.Photo;

import java.io.IOException;

public interface PhotoService {

    void downloadBlob(String blobName, String downloadLocation);

    String findBlobLinkByBlobName(String blobName);

    //String findBlobNameByPhotoLink(String photoLink);

    Photo createPhoto(PhotoDto photoDto, MultipartFile multiPartFile, String blobName) throws IOException;

    String findPhotoLinkByPhotoId(Long photoId);

    void deletePhotoFromDatabase(Long photoId, String photoLink);

    void deletePhotoFromDatabase(Long photoId);

    Photo findPhotoById(Long id);

}
