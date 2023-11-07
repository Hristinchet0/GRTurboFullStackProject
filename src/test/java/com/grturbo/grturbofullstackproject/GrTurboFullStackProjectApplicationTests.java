package com.grturbo.grturbofullstackproject;

import com.grturbo.grturbofullstackproject.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class GrTurboFullStackProjectApplicationTests {

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Test
    void contextLoads() {
        userServiceImpl.init();
    }


}
