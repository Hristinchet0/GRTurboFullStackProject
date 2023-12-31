package com.grturbo.grturbofullstackproject;

import com.grturbo.grturbofullstackproject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableConfigurationProperties
@SpringBootApplication
public class GrTurboFullStackProjectApplication implements CommandLineRunner {

    private final UserServiceImpl userServiceImpl;

    public GrTurboFullStackProjectApplication(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    public static void main(String[] args) {
        SpringApplication.run(GrTurboFullStackProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userServiceImpl.init();
    }
}
