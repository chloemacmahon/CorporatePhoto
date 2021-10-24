package za.ac.nwu.ac.logic.implementation;

import com.azure.storage.blob.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.nwu.ac.domain.dto.PhotoDto;
import za.ac.nwu.ac.domain.exception.PhotoDoesNotExistException;
import za.ac.nwu.ac.domain.exception.PhotoLinkNotFoundException;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.logic.configuration.PhotoStorageConfig;
import za.ac.nwu.ac.logic.service.PhotoService;
import za.ac.nwu.ac.repository.PhotoRepository;

@Data
@Service
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private String connectionString;

    private String blobEndPoint;

    private String containerName;

    private BlobServiceClient blobServiceClient;

    private BlobContainerClient blobContainerClient;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository, PhotoStorageConfig photoStorageConfig){
        this.photoRepository = photoRepository;
        connectionString = photoStorageConfig.getConnectionString();
        blobEndPoint = photoStorageConfig.getBlobEndPoint();
        containerName = photoStorageConfig.getContainerName();
        setBlobServiceClient();
        setBlobContainerClient();
    }


    // --> Creating Clients for Azure Operations <--
    //Blob Service Client


    public void setBlobServiceClient() {
        this.blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(getBlobEndPoint())
                .sasToken(getConnectionString())
                .buildClient();
    }

    public void setBlobContainerClient() {
        this.blobContainerClient = new BlobContainerClientBuilder()
                .endpoint(getBlobEndPoint())
                .sasToken(getConnectionString())
                .containerName(getContainerName())
                .buildClient();

    }

    // Creating BlobClient
    public BlobClient createBlobClient(String blobName)
    {
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        return blobClient;
    }

    // Creating Container
    public void CreateNewContainer(String containerName)
    {
        blobServiceClient.createBlobContainer(containerName);
    }

    // Uploading a blob

    public void uploadBlob(String blobPath)
    {
        //TODO: check file to to make sure it one of the image data types
        //TODO: check if file already exists...
        BlobClient blobClient = blobContainerClient.getBlobClient(containerName);
        //specify the file that needs to be uploaded i.e. the path of the file.
        blobClient.uploadFromFile(blobPath.toString());
        //blobClient.getBlobName();
    }

    /**
     * Downloads image
     * @param blobName - blob name
     */

    public void downloadBlob(String blobName, String downloadLocation)
    {
        //creates blob client so we can get the blob name and download it.
        createBlobClient(blobName)
                .downloadToFile(downloadLocation); //TODO: set the download location
        //blobClient.downloadToFile("downloaded-file.jpg");
    }

    // This method retrieves the blob Link from Storage Container (cloud Storage) via the name of the blob
    // as it is stored in the Storage Container
    public String findBlobLinkByBlobName(String blobName){
        return createBlobClient(blobName).getBlobUrl();
    }

    // This method retrieves the blob name, by using the link stored in the database.
    // This needs to be done as the image/file/blob name is not stored in the database.
    public String findBlobNameByPhotoLink(String photoLink){
        if(photoLink == null){
            throw new PhotoLinkNotFoundException("The photo link could not be found");
        }
        String blobName = photoLink.substring(photoLink.lastIndexOf('/')+1);

        return blobName;
    }

    //Create, adding image to database
    public Photo createPhoto(PhotoDto photoDto){
        Photo photo = new Photo(photoDto.getPhotoLink(), photoDto.getPhotoMetaData());
        return photoRepository.save(photo);
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
    public String findPhotoLinkByPhotoId(Long photoId){
        if(photoRepository.findById(photoId).isPresent())
            return photoRepository.findById(photoId).get().toString();
        else
            throw new PhotoDoesNotExistException();
    }
    //Delete, deleting a photo from db and blob from Storage
    public void deletePhotoFromDatabase(Long photoId, String photoLink){
        photoRepository.deleteById(photoId);
        createBlobClient(findBlobNameByPhotoLink(photoLink)).delete();
    }

    //Search Metadata

    @Override
    public String toString() {
        return "PhotoServicesImpl{" +
                "connectionString='" + connectionString + '\'' +
                ", blobEndPoint='" + blobEndPoint + '\'' +
                ", containerName='" + containerName + '\'' +
                '}';
    }

    //    public String getPhotoLinkFromDb(){
//        photoRepository.findById().get().getPhotoLink()
//    }
}
