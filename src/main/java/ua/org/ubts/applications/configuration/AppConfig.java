package ua.org.ubts.applications.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@Slf4j
public class AppConfig {

    private static final String INIT_DIRECTORIES_ERROR_MESSAGE = "Could not create application directories";

    @Bean
    public String appDirectory() {
        return System.getProperty("user.home") + File.separator + "ubts-applications";
    }

    @Bean
    public String tmpDirectory() {
        return appDirectory() + File.separator + "tmp";
    }

    @PostConstruct
    public void initDirectories() {
        try {
            Files.createDirectories(Paths.get(appDirectory()));
            Files.createDirectories(Paths.get(tmpDirectory()));
        } catch (IOException e) {
            log.error(INIT_DIRECTORIES_ERROR_MESSAGE, e);
        }
    }

}
