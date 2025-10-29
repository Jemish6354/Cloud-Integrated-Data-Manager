package com.example.scm.Services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.scm.Helper.AppConstants;
import com.example.scm.Services.ImageService;

@Service
public class ImageServiceImpl implements ImageService{

    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadeImage(MultipartFile contactImage, String filename) {
        

        // upload image to cloud and return url (aws s3 or cloudnary)

         

        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                
                "public_id", filename

            ));

            return this.getURLFromPublicId(filename);



        } catch (IOException e) {
            
            e.printStackTrace();
            return null;
        }

        
    }

    @Override
    public String getURLFromPublicId(String publicId) {

        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
                .width(AppConstants.CONTACT_IMAGE_WIDTH)
                .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                .crop(AppConstants.CONTACT_IMAGE_CROP)
        )
        .generate(publicId);

    }
        

    

}
