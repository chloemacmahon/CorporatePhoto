package za.ac.nwu.ac.logic.implementation;

import com.azure.storage.blob.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import za.ac.nwu.ac.logic.configuration.PhotoProperties;
import za.ac.nwu.ac.logic.service.PhotoServices;
import za.ac.nwu.ac.repository.PhotoRepository;

@Service
public class PhotoServicesImpl implements PhotoServices {

    private final PhotoRepository photoRepository;
    @Autowired
    private PhotoProperties photoProperties;

    @Autowired
    public PhotoServicesImpl(PhotoRepository photoRepository){
        this.photoRepository = photoRepository;
    }


    // --> Creating Clients for Azure Operations <--
    //Blob Service Client
    BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
            .endpoint(photoProperties.getBlobEndPoint())
            .sasToken(photoProperties.getConnectionString())
            .buildClient();

    // Creating blob container client
    BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
            .endpoint(photoProperties.getBlobEndPoint())
            .sasToken(photoProperties.getConnectionString())
            .containerName(photoProperties.getContainerName())
            .buildClient();


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

    public void uploadImage(String userID, String path)
    {
        //TODO: check file to to make sure it one of the image data types
        //TODO: check if file already exists...

        // use the user id to find the container the blob is in (file)
        BlobClient blobClient = blobContainerClient.getBlobClient(userID.toString());
        //specify the file that needs to be uploaded i.e. the path of the file.
        blobClient.uploadFromFile(path.toString());
    }

    //Downloading Blob
    public void downloadImage(Long blobName)
    {
        //creates blob client so we can get the blob name and download it.
        createBlobClient(blobName.toString())
                .downloadToFile(blobName.toString()); //TODO: set the dowload location
        //blobClient.downloadToFile("downloaded-file.jpg");
    }


//    public String getPhotoLinkFromDb(){
//        photoRepository.findById().get().getPhotoLink()
//    }
}
