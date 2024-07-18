package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.ImageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Image;
import org.springframework.stereotype.Component;


@Component
public interface ImageService {
    Long uploadImage(ImageDTO imageDTO, Long id);
    void updateImage(ImageDTO imageDTO);
    void deleteImage(ImageDTO imageDTO);
    ImageDTO getImageByUserId(Long userId);
    ImageDTO getImageByBookId(Long bookId);
    ImageDTO getImageByPageId(Long pageId);
    ImageDTO getImageByStepId(Long stepId);

}
