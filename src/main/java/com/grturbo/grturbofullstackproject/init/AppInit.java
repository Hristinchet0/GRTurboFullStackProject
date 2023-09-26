package com.grturbo.grturbofullstackproject.init;

import com.grturbo.grturbofullstackproject.service.UserService;
import org.springframework.boot.CommandLineRunner;

public class AppInit implements CommandLineRunner {

    private final UserService userService;

    public AppInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        userService.init();
    }
}
