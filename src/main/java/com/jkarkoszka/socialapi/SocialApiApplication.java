package com.jkarkoszka.socialapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
public class SocialApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialApiApplication.class, args);
    }

}
