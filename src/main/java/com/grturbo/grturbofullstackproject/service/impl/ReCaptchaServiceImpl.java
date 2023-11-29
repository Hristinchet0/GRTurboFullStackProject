package com.grturbo.grturbofullstackproject.service.impl;

import com.grturbo.grturbofullstackproject.config.ReCaptchaConfig;
import com.grturbo.grturbofullstackproject.model.dto.ReCaptchaResponseDTO;
import com.grturbo.grturbofullstackproject.service.ReCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.Optional;

@Service
public class ReCaptchaServiceImpl implements ReCaptchaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReCaptchaServiceImpl.class);

    private final RestTemplate restTemplate;
    private final ReCaptchaConfig reCaptchaConfig;

    public ReCaptchaServiceImpl(RestTemplate restTemplate, ReCaptchaConfig reCaptchaConfig) {
        this.restTemplate = restTemplate;
        this.reCaptchaConfig = reCaptchaConfig;
    }

    @Override
    public Optional<ReCaptchaResponseDTO> verify(String token) {
        String url = buildRecaptchaURL();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Prepare request body
        String requestBody = "secret=" + reCaptchaConfig.getSecret() + "&response=" + token;
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<ReCaptchaResponseDTO> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    ReCaptchaResponseDTO.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return Optional.ofNullable(responseEntity.getBody());
            }
        } catch (Exception e) {
            LOGGER.error("Error fetching google response...", e);
        }

        return Optional.empty();
    }

    private String buildRecaptchaURL() {
        return "https://www.google.com/recaptcha/api/siteverify";
    }
}

