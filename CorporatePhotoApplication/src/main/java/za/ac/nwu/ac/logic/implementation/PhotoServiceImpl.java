package za.ac.nwu.ac.logic.implementation;

import com.azure.storage.blob.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.ac.nwu.ac.domain.dto.PhotoDto;
import za.ac.nwu.ac.domain.exception.CouldNotDeletePhotoException;
import za.ac.nwu.ac.domain.exception.PhotoDoesNotExistException;
import za.ac.nwu.ac.domain.exception.PhotoLinkNotFoundException;
import za.ac.nwu.ac.domain.exception.PhotoMetaDataDoesNotExistException;
import za.ac.nwu.ac.domain.persistence.UserAccount;
import za.ac.nwu.ac.domain.persistence.album.Album;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.domain.persistence.photo.PhotoMetaData;
import za.ac.nwu.ac.logic.configuration.PhotoStorageConfig;
import za.ac.nwu.ac.logic.service.AlbumService;
import za.ac.nwu.ac.logic.service.PhotoMetaDataService;
import za.ac.nwu.ac.logic.service.PhotoService;
import za.ac.nwu.ac.repository.AlbumRepository;
import za.ac.nwu.ac.repository.PhotoMetaDataRepository;
import za.ac.nwu.ac.repository.PhotoRepository;
import za.ac.nwu.ac.repository.UserAccountRepository;

import java.io.IOException;
import java.util.List;

@Data
@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private final PhotoMetaDataRepository photoMetaDataRepository;

    private final PhotoMetaDataService photoMetaDataService;

    private final UserAccountRepository userAccountRepository;

    private final AlbumService albumService;

    private String connectionString;

    private String blobEndPoint;

    private String containerName;

    private String blobSasToken;

    private BlobServiceClient blobServiceClient;

    private BlobContainerClient blobContainerClient;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoMetaDataRepository photoMetaDataRepository, PhotoMetaDataService photoMetaDataService, UserAccountRepository userAccountRepository, AlbumRepository albumRepository, AlbumService albumService, PhotoStorageConfig photoStorageConfig) {
        this.photoRepository = photoRepository;
        this.photoMetaDataRepository = photoMetaDataRepository;
        this.photoMetaDataService = photoMetaDataService;
        this.userAccountRepository = userAccountRepository;
        this.albumService = albumService;
        connectionString = photoStorageConfig.getConnectionString();
        blobEndPoint = photoStorageConfig.getBlobEndPoint();
        containerName = photoStorageConfig.getContainerName();
        blobSasToken = photoStorageConfig.getBlobSasToken();
        setBlobServiceClient();
        setBlobContainerClient();
    }


    // --> Creating Clients for Azure Operations <--
    //Blob Service Client


    public void setBlobServiceClient() {
        this.blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(getBlobEndPoint())
                .sasToken(getBlobSasToken())
                .buildClient();
    }

    public void setBlobContainerClient() {
        this.blobContainerClient = new BlobContainerClientBuilder()
                .endpoint(getBlobEndPoint())
                .sasToken(getBlobSasToken())
                .containerName(getContainerName())
                .buildClient();

    }

    // Creating BlobClient
    public BlobClient createBlobClient(String blobName) {
        return blobContainerClient.getBlobClient(blobName);
    }

    // Creating Container
    public void CreateNewContainer(String containerName) {
        blobServiceClient.createBlobContainer(containerName);
    }

    // Uploading a blob

    public String uploadBlob(MultipartFile multiPartFile, String blobName) throws IOException {
        //TODO: check file to to make sure it one of the image data types
        //TODO: check if file already exists...
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        //specify the file that needs to be uploaded i.e. the path of the file.
        blobClient.upload(multiPartFile.getInputStream(), multiPartFile.getSize(), true);
        //blobClient.getBlobName();
        return blobClient.getBlobUrl();
    }

    /**
     * Downloads image
     *
     * @param blobName - blob name
     */

    public void downloadBlob(String blobName, String downloadLocation) {
        //creates blob client so we can get the blob name and download it.
        createBlobClient(blobName)
                .downloadToFile(downloadLocation); //TODO: set the download location
        //blobClient.downloadToFile("downloaded-file.jpg");
    }

    // This method retrieves the blob Link from Storage Container (cloud Storage) via the name of the blob
    // as it is stored in the Storage Container
    public String findBlobLinkByBlobName(String blobName) {
        return createBlobClient(blobName).getBlobUrl();
    }

    // This method retrieves the blob name, by using the link stored in the database.
    // This needs to be done as the image/file/blob name is not stored in the database.
    //TODO: change this to take the photo link and check for photo name in Table, photoName attribute still needs to be added
    public String findBlobNameByPhotoLink(String photoLink) {
        if (photoLink == null) {
            throw new PhotoLinkNotFoundException("The photo link could not be found");
        }
        return photoLink.substring(photoLink.lastIndexOf('/') + 1);
    }

    //Create, adding image to database
    public Photo createPhoto(PhotoDto photoDto, MultipartFile multiPartFile, String blobName) throws IOException {
        try {
            String photoLink = uploadBlob(multiPartFile, blobName);
            Photo photo = new Photo(photoLink, photoDto.getPhotoMetaData());
            /*Photo newPhoto = photoRepository.save(photo);
            newPhoto.setSharablePhotoLink(newPhoto.generateSharablePhotoLink());*/
            return photoRepository.save(photo);
        } catch (Exception e){
            throw new IOException(e.getLocalizedMessage());
        }
    }

    //TODO: The update still needs work, the approach forces you to copy the photo/file and could take long.
    //Update, changing the name of photo
//    public Photo updatePhotoName(PhotoDto photoDto, String newPhotoName){
//        String photoLink = photoDto.getPhotoLink();
//        // first we need to copy the original
//        createBlobClient(getBlobNameByPhotoLink(photoLink)).copyFromUrl(photoLink);
//        // then we update the database
//
//        //then we delete the original in Storage Container
//        createBlobClient(getBlobNameByPhotoLink(photoLink)).delete();
//    }
    //Read, getting the photo link
    public String findPhotoLinkByPhotoId(Long photoId) {
        if (photoRepository.findById(photoId).isPresent())
            return photoRepository.findById(photoId).get().getPhotoLink();
        else
            throw new PhotoDoesNotExistException();
    }

    //Delete, deleting a photo from db and blob from Storage
    public void deletePhotoFromDatabase(Long photoId, String photoLink) {
        try {
            Photo photo = photoRepository.findById(photoId).get();
            List<Album> albums = albumService.findAlbumsThatContainsPhoto(photoId);
            for (Album album : albums) {
                albumService.deletePhotoFromAlbum(photoId, album.getAlbumId());
            }
            UserAccount userAccount = findPhotoById(photoId).getPhotoMetaData().getOwner();
            photoMetaDataRepository.deleteById(findPhotoById(photoId).getPhotoMetaData().getMetaDataId());
            photoRepository.deleteById(photoId);
            userAccountRepository.save(userAccount);
            createBlobClient(findBlobNameByPhotoLink(photoLink)).delete();
        } catch (Exception e) {
            throw new CouldNotDeletePhotoException("Photo could not be removed, nested exception is (" + e.getLocalizedMessage() + ")");
        }
    }

    public void deletePhotoFromDatabase(Long photoId) {
        try {
            String photoLink = findPhotoLinkByPhotoId(photoId);
            deletePhotoFromDatabase(photoId, photoLink);
        } catch (Exception e) {
            throw new CouldNotDeletePhotoException(e.getLocalizedMessage());
        }
    }

    public Photo findPhotoById(Long id) {
        try {
            return photoRepository.findById(id).get();
        } catch (Exception e) {
            throw new PhotoDoesNotExistException();
        }
    }

    public Photo findPhotoBySharableLink(String sharableLink){
        if (photoRepository.findPhotoBySharablePhotoLink(sharableLink) != null)
            return photoRepository.findPhotoBySharablePhotoLink(sharableLink);
        else
            throw new PhotoDoesNotExistException("Sharable link invalid");
    }

    public PhotoMetaData findPhotoMetaDataId(Long id){
        try{
            Photo photo = photoRepository.findById(id).get();
            return photo.getPhotoMetaData();
        } catch (Exception e){
            throw new PhotoMetaDataDoesNotExistException();
        }
    }


    //Search Metadata

}
