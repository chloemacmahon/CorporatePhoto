package za.ac.nwu.ac.logic.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application-blob.properties")
public class PhotoProperties {
    @Value("${azure.meblob.connectionString}")
    private String connectionString;
    @Value("${azure.meblob.container}")
    private String containerName;
    @Value("${azure.meblob.blobEndPoint}")
    private String blobEndPoint;

    public PhotoProperties() {
    }

    public PhotoProperties(String connectionString, String containerName, String blobEndPoint) {
        this.connectionString = connectionString;
        this.containerName = containerName;
        this.blobEndPoint = blobEndPoint;
    }
}
