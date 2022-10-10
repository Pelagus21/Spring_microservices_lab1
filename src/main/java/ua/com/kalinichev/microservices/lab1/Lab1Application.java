package ua.com.kalinichev.microservices.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class Lab1Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab1Application.class, args);
    }

}