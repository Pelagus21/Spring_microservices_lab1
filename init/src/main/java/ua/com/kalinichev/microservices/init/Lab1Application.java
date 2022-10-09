package ua.com.kalinichev.microservices.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages={
		"ua.com.kalinichev.microservices.core",
		"ua.com.kalinichev.microservices.schedule",
		"ua.com.kalinichev.microservices.init"})
@EnableJpaRepositories(basePackages="ua.com.kalinichev.microservices.core.repositories")
@EntityScan(basePackages="ua.com.kalinichev.microservices.core.models")
public class Lab1Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab1Application.class, args);
	}

}
