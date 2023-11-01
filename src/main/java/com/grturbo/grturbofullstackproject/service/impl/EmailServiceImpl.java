package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.service.EmailService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    public String emailUsername;

    @Value("${spring.mail.password}")
    public String emailPassword;

    @Override
    public void sendEmail(String to, String subject, String text) {
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator(emailUsername, emailPassword));
        email.setSSLOnConnect(true);
        try {
            email.setFrom("grturbo@grturbo.com");
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
        email.setSubject(subject);
        try {
            email.setMsg(text);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
        try {
            email.addTo(to);
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
        try {
            email.send();
        } catch (EmailException e) {
            throw new RuntimeException(e);
        }
    }
}
