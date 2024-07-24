package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.ImageDTO;;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/*
Methods that use image repository queries to create, get, and update a image .
image entities are changed into a DTO (Data Transfer Object).
 */

@Component
public interface ImageService {

    void uploadImage(MultipartFile file, ImageDTO image, Long id) throws IOException;
    void updateImage(MultipartFile file,ImageDTO imageDTO);
    void deleteImage(ImageDTO imageDTO);
    ImageDTO getImageById(Long id);;
    ImageDTO getImageByUserId(Long userId);
    ImageDTO getImageByBookId(Long bookId);
    ImageDTO getImageByPageId(Long pageId);
    ImageDTO getImageByStepId(Long stepId);

}
