package org.dara.authenticationservice;

import org.dara.authenticationservice.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class AuthenticationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

}
