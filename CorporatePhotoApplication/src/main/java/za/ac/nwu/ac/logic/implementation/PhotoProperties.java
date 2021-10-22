package za.ac.nwu.ac.logic.implementation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("azure.meblob")
public class PhotoProperties {
    private String connstring;
    private String container;
    private String blobendpoint;

    public String getConnectionString() {
        return this.connstring;
    }

    public String getContainerName(){
        return this.container;
    }

    public String getblobEndPoint(){
        return this.container;
    }
}
