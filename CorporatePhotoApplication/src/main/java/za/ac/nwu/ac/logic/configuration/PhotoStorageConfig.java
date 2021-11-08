package za.ac.nwu.ac.logic.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


//@PropertySource("classpath:application-blob.properties")
@Configuration
@PropertySource("classpath:application-blob.properties")
@ConfigurationProperties(prefix = "azure.myblob")
public class PhotoStorageConfig {

    private String connectionString;

    private String containerName;

    private String blobEndPoint;

    private String blobSasToken;

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getBlobEndPoint() {
        return blobEndPoint;
    }

    public void setBlobEndPoint(String blobEndPoint) {
        this.blobEndPoint = blobEndPoint;
    }

    public String getBlobSasToken(){
        return blobSasToken;
    }

    public void setBlobSasToken(String sasToken) {
        this.blobSasToken = sasToken;
    }
}
