package za.ac.nwu.ac.repository.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "za.ac.nwu.ac.repository")
@EntityScan({"za.ac.nwu.ac.dto"})
public class RepositoryConfig {
}
