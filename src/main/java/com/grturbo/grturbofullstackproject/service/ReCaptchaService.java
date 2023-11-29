package com.grturbo.grturbofullstackproject.service;

import com.grturbo.grturbofullstackproject.model.dto.ReCaptchaResponseDTO;

import java.util.Optional;

public interface ReCaptchaService {

    Optional<ReCaptchaResponseDTO> verify(String token);
}
