package za.ac.nwu.ac.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import za.ac.nwu.ac.logic.configuration.PhotoStorageConfig;

@SpringBootApplication
@EnableConfigurationProperties({PhotoStorageConfig.class})
public class CorporatePhotoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CorporatePhotoApplication.class,args);
    }
}
