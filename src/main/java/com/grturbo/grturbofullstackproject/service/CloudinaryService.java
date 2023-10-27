package com.grturbo.grturbofullstackproject.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    String uploadImage(MultipartFile multipartFile);
}
