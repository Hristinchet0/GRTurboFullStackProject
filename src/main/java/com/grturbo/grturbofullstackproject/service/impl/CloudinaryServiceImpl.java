package com.grturbo.grturbofullstackproject.service.impl;

import com.cloudinary.Cloudinary;
import com.grturbo.grturbofullstackproject.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private static final String TEMP_FILE = "temp-file";

    private static final String URL = "url";

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) {
        File file = null;
        try {
            file = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return this.cloudinary
                    .uploader()
                    .upload(file, Collections.emptyMap())
                    .get(URL)
                    .toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}




