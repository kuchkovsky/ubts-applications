package ua.org.ubts.applications.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AppConfig {

    @Bean
    public String appDirectory() {
        return System.getProperty("user.home") + File.separator + "UBTS-ApplSystem";
    }

}
