package ua.com.kalinichev.microservices.lab1.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.com.kalinichev.microservices.lab1.utils.Utils;
import ua.com.kalinichev.microservices.lab1.utils.UtilsImpl;

@Configuration
public class Config {

    @Bean
    public Utils getUtils(){
        return new UtilsImpl();
    }

}
