package com.estheryoo.yoo_esther_cookingmemories_casestudy.service.implentation;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.ImageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Image;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Book;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Page;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Step;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.ImageRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipeBookRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipePageRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipeStepRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.ImageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipePageRepository recipePageRepository;
    private final RecipeBookRepository recipeBookRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository,
                            RecipeStepRepository recipeStepRepository,
                            RecipePageRepository recipePageRepository,
                            RecipeBookRepository recipeBookRepository) {
        this.imageRepository = imageRepository;
        this.recipeStepRepository = recipeStepRepository;
        this.recipePageRepository = recipePageRepository;
        this.recipeBookRepository = recipeBookRepository;
    }

    @Override
    @Transactional
    public Long uploadImage(ImageDTO imageDTO, Long id) {
        try {
            //create new image entity
            Image imageEntity = new Image();
            imageEntity.setUploaded(imageDTO.getUploaded());
            Image savedImage = imageRepository.save(imageEntity);

            if(imageDTO.getLinked() == "step"){
            Recipe_Step step = recipeStepRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Step not found"));
            step.setImage(savedImage);
            recipeStepRepository.save(step);
            }else if(imageDTO.getLinked() =="page"){
                Recipe_Page page = recipePageRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Page not found"));
                page.setImage(savedImage);
                recipePageRepository.save(page);
            }else if(imageDTO.getLinked() =="book"){
                Recipe_Book book = recipeBookRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Book not found"));
                book.setImage(savedImage);
                recipeBookRepository.save(book);
            }else{
                throw new RuntimeException("Could not link image");
            }

            return savedImage.getId();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to store image data", ex);
        }
    }

    @Override
    public void updateImage(ImageDTO imageDTO) {
        Image imageEntity = imageRepository.findById(imageDTO.getId())
                .orElseThrow(()-> new RuntimeException("Image not found"));

        imageEntity.setUploaded(imageDTO.getUploaded());
        imageRepository.save(imageEntity);
    }

    @Override
    public void deleteImage(ImageDTO imageDTO){
        Image imageEntity = imageRepository.findById(imageDTO.getId())
                .orElseThrow(()-> new RuntimeException("Image not found"));
        imageRepository.delete(imageEntity);
    }



}
