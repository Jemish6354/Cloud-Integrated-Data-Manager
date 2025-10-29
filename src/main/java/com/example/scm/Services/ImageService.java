package com.example.scm.Services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadeImage(MultipartFile contactImage, String filename);

    String getURLFromPublicId(String publicId);

}
