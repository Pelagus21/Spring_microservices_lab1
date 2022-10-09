package ua.com.kalinichev.microservices.core.configurations;

import ua.com.kalinichev.microservices.core.utils.Utils;
import ua.com.kalinichev.microservices.core.utils.UtilsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Utils getUtils(){
        return new UtilsImpl();
    }

}
