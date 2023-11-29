package com.grturbo.grturbofullstackproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReCaptchaConfig {

    @Value("${google.recaptcha.site}")
    private String site;

    @Value("${google.recaptcha.secret}")
    private String secret;

    public String getSite() {
        return site;
    }

    public ReCaptchaConfig setSite(String site) {
        this.site = site;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public ReCaptchaConfig setSecret(String secret) {
        this.secret = secret;
        return this;
    }
}
