package ua.org.ubts.applicationssystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ua.org.ubts.applicationssystem.configuration.JpaConfiguration;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"ua.org.ubts.applicationssystem"})
public class UbtsApplicationsSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(UbtsApplicationsSystemApplication.class, args);
	}
}
