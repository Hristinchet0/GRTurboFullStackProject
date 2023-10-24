package com.grturbo.grturbofullstackproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GrTurboFullStackProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrTurboFullStackProjectApplication.class, args);
    }

}
