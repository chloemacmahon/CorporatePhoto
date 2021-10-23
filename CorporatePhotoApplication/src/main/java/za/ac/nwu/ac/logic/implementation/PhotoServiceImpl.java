package za.ac.nwu.ac.logic.implementation;

import com.azure.storage.blob.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    /*BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
            .endpoint("https://corporatephoto.blob.core.windows.net/testcontainer")
            .sasToken("https://corporatephoto.blob.core.windows.net/;SharedAccessSignature=sv=2020-08-04&ss=b&srt=sco&sp=rwdlacx&se=2021-12-23T16:37:57Z&st=2021-10-22T08:37:57Z&spr=https&sig=%2FnLtr4RPmp8fQS1qVwALNxeN0K92ykn%2FQhH%2Fzna7o8k%3D")//photoProperties.getConnectionString())
            .buildClient();*/

    // Creating blob container client
    /*BlobContainerClient blobContainerClient = new BlobContainerClientBuilder()
            .endpoint("https://corporatephoto.blob.core.windows.net/testcontainer")//photoProperties.getBlobEndPoint())
            .sasToken("https://corporatephoto.blob.core.windows.net/;SharedAccessSignature=sv=2020-08-04&ss=b&srt=sco&sp=rwdlacx&se=2021-12-23T16:37:57Z&st=2021-10-22T08:37:57Z&spr=https&sig=%2FnLtr4RPmp8fQS1qVwALNxeN0K92ykn%2FQhH%2Fzna7o8k%3D")//photoProperties.getConnectionString())
            .containerName("corporatephoto")//photoProperties.getContainerName())
            .buildClient();

*/
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

    /**
     * Downloads image
     * @param blobName - blob name
     */

    public void downloadImage(Long blobName)
    {
        //creates blob client so we can get the blob name and download it.
        createBlobClient(blobName.toString())
                .downloadToFile(blobName.toString()); //TODO: set the download location
        //blobClient.downloadToFile("downloaded-file.jpg");
    }

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
