package com.grturbo.grturbofullstackproject.web;

import javax.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LoginControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    private String baseUrl = "http://localhost";

    @Test
    void testLoginPageShown() throws Exception {
        mockMvc.perform(get("/login")).
                andExpect(status().isOk()).
                andExpect(view().name("login"));
    }

    @Test
    void testUserRegistrationSuccessfully() throws Exception {
        mockMvc.perform(post("/register").
                        param("email", "anna@example.com").
                        param("username", "anna").
                        param("firstName", "Anna").
                        param("lastName", "Petrova").
                        param("password", "topsecret").
                        param("confirmPassword", "topsecret").
                        cookie(new Cookie("lang", Locale.ENGLISH.getLanguage())).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl(baseUrl + "/login"));
    }

    @Test
    void testUserRegistrationUnsuccessfully() throws Exception {
        mockMvc.perform(post("/register").
                        param("email", "annaexample.com").
                        param("username", "anna").
                        param("firstName", "Anna").
                        param("lastName", "Petrova").
                        param("password", "topsecret").
                        param("confirmPassword", "topsecret").
                        cookie(new Cookie("lang", Locale.ENGLISH.getLanguage())).
                        with(csrf())
                ).
                andExpect(status().is3xxRedirection()).
                andExpect(redirectedUrl("/login"));
    }


}

