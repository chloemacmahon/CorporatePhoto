package za.ac.nwu.ac.logic.implementation;

import com.azure.storage.blob.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import za.ac.nwu.ac.domain.dto.PhotoDto;
import za.ac.nwu.ac.domain.persistence.photo.Photo;
import za.ac.nwu.ac.logic.service.PhotoServices;
import za.ac.nwu.ac.repository.PhotoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
@Service
@Slf4j
public class PhotoServicesImpl implements PhotoServices {

    private final PhotoRepository photoRepository;
    private PhotoProperties photoProperties;

    @Autowired
    public PhotoServicesImpl(PhotoRepository photoRepository){
        this.photoRepository = photoRepository;
        this.photoProperties = photoProperties;
    }


    // --> Creating Clients for Azure Operations <--
    //Blob Service Client
    BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
            .endpoint(photoProperties.getblobEndPoint())
            .sasToken(photoProperties.getConnectionString())
            .buildClient();

    // Creating blob container client
    BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
            .endpoint(photoProperties.getblobEndPoint())
            .sasToken(photoProperties.getConnectionString())
            .containerName(photoProperties.getContainerName())
            .buildClient();


    // Creating BlobClient
    public BlobClient CreateBlobClient(String blobName)
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

    public void UploadImage(String userID, String path)
    {
        //TODO: check file to to make sure it one of the image data types
        //TODO: check if file already exists...

        // use the user id to find the container the blob is in (file)
        BlobClient blobClient = blobContainerClient.getBlobClient(userID.toString());
        //specify the file that needs to be uploaded i.e. the path of the file.
        blobClient.uploadFromFile(path.toString());
    }

    //Downloading Blob
    public void DownloadImage(Long blobName)
    {
        //creates blob client so we can get the blob name and download it.
        CreateBlobClient(blobName.toString())
                .downloadToFile(blobName.toString()); //TODO: set the dowload location
        //blobClient.downloadToFile("downloaded-file.jpg");
    }
}
