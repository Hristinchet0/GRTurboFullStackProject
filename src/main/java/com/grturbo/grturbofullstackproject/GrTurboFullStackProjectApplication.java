package com.grturbo.grturbofullstackproject;

import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GrTurboFullStackProjectApplication implements CommandLineRunner {

    private final UserService userService;

    public GrTurboFullStackProjectApplication(UserService userService) {
        this.userService = userService;
    }


    public static void main(String[] args) {
        SpringApplication.run(GrTurboFullStackProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        userService.init();
    }
}
