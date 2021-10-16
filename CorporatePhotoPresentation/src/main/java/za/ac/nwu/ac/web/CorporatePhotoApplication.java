package za.ac.nwu.ac.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CorporatePhotoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CorporatePhotoApplication.class,args);
    }
}
