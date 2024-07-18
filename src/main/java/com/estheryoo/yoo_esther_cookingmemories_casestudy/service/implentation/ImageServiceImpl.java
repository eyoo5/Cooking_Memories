package com.estheryoo.yoo_esther_cookingmemories_casestudy.service.implentation;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.ImageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipePageRepository recipePageRepository;
    private final RecipeBookRepository recipeBookRepository;
    private final UserRepository userRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository,
                            RecipeStepRepository recipeStepRepository,
                            RecipePageRepository recipePageRepository,
                            RecipeBookRepository recipeBookRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.recipeStepRepository = recipeStepRepository;
        this.recipePageRepository = recipePageRepository;
        this.recipeBookRepository = recipeBookRepository;
        this.userRepository = userRepository;
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
            }else if(imageDTO.getLinked() == "book"){
                Recipe_Book book = recipeBookRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Book not found"));
                book.setImage(savedImage);
                recipeBookRepository.save(book);
            } else if ( imageDTO.getLinked() == "user") {
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.setImage(savedImage);
                userRepository.save(user);
            } else{
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

    @Override
    public ImageDTO getImageByUserId(Long userId) {
        Image image = imageRepository.findByUserId(userId);
        if(image != null){
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setId(image.getId());
            imageDTO.setUploaded(image.getUploaded());
            imageDTO.setLinked(image.getLinked());
            return imageDTO;
        }else{
            throw new RuntimeException("Image not found");
        }
    }

    @Override
    public ImageDTO getImageByBookId(Long bookId) {
        Image image = imageRepository.findByBookId(bookId);
        if(image != null){
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setId(image.getId());
            imageDTO.setUploaded(image.getUploaded());
            imageDTO.setLinked(image.getLinked());
            return imageDTO;
        }else{
            throw new RuntimeException("Image not found");
        }
    }

    @Override
    public ImageDTO getImageByPageId(Long pageId) {
        Image image = imageRepository.findByPageId(pageId);

        if(image != null){
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setId(image.getId());
            imageDTO.setUploaded(image.getUploaded());
            imageDTO.setLinked(image.getLinked());
            return imageDTO;
        }else{
            throw new RuntimeException("Image not found");
        }
    }

    @Override
    public ImageDTO getImageByStepId(Long stepId) {
        Image image = imageRepository.findByStepId(stepId);
        if(image != null){
            ImageDTO imageDTO = new ImageDTO();
            imageDTO.setId(image.getId());
            imageDTO.setUploaded(image.getUploaded());
            imageDTO.setLinked(image.getLinked());
            return imageDTO;
        }else{
            throw new RuntimeException("Image not found");
        }
    }

}
