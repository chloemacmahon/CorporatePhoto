package za.ac.nwu.ac.web.configuration;

import za.ac.nwu.ac.logic.configuration.LogicConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import za.ac.nwu.ac.repository.configuration.RepositoryConfig;

@Configuration
@Import({RepositoryConfig.class, LogicConfig.class})
@ComponentScan(basePackages = {
        "za.ac.nwu.ac.web.controller"
})
public class WebConfig {
}
