package com.estheryoo.yoo_esther_cookingmemories_casestudy.service.implentation;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.ImageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.ImageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;



/*
Methods connect the repository to find image information and
converts this into a image DTO (Data Transfer Object).
It can save and retrieve images from the database.
*/

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipePageRepository recipePageRepository;
    private final RecipeBookRepository recipeBookRepository;
    private final UserRepository userRepository;;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository,
                            RecipeStepRepository recipeStepRepository,
                            RecipePageRepository recipePageRepository,
                            RecipeBookRepository recipeBookRepository,
                            UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.recipeStepRepository = recipeStepRepository;
        this.recipePageRepository = recipePageRepository;
        this.recipeBookRepository = recipeBookRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void uploadImage(MultipartFile file, ImageDTO imageDTO, Long id) throws IOException {
        Image image = new Image();

        try{
            byte[] compressedImageFile = ImageUtils.compressImage(file.getBytes());
            image.setUploaded(compressedImageFile);
        }catch (IOException e){
            e.printStackTrace();
        }

        image.setType(file.getContentType());
        image.setLinked(imageDTO.getLinked());
        Image savedImage = imageRepository.save(image);

        String linked = imageDTO.getLinked();
        //checking to see who the image belongs to and making a one to one connection:
        if("step".equals(linked)){
            Recipe_Step step = recipeStepRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Step not found"));
            step.setImage(savedImage);
            recipeStepRepository.save(step);
        }else if("page".equals(linked)){
                Recipe_Page page = recipePageRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Page not found"));
                page.setImage(savedImage);
                recipePageRepository.save(page);
        }else if("book".equals(linked)){
                Recipe_Book book = recipeBookRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Book not found"));
                book.setImage(savedImage);
                recipeBookRepository.save(book);
        } else if ("user".equals(linked)) {
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                user.setImage(savedImage);
                userRepository.save(user);
        } else{
                throw new RuntimeException("Could not link image");
        }

    }



    @Override
    public void updateImage(MultipartFile file,ImageDTO imageDTO) {
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
    public ImageDTO getImageById(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(()-> new RuntimeException("Image not found"));
        if(image != null){
            return convertEntityToDTO(image);
        }else{
            throw new RuntimeException("Image not found");
        }
    }

    @Override
    public ImageDTO getImageByUserId(Long userId) {
        Image image = imageRepository.findByUserId(userId);
        if(image != null){
            return convertEntityToDTO(image);
        }else{
            throw new RuntimeException("Image not found");
        }
    }

    @Override
    public ImageDTO getImageByBookId(Long bookId) {
        Image image = imageRepository.findByBookId(bookId);
        if(image != null){
            return convertEntityToDTO(image);
        }else{
            throw new RuntimeException("Image not found");
        }
    }

    @Override
    public ImageDTO getImageByPageId(Long pageId) {
        Image image = imageRepository.findByPageId(pageId);

        if(image != null){
            return convertEntityToDTO(image);
        }else{
            throw new RuntimeException("Image not found");
        }
    }

    @Override
    public ImageDTO getImageByStepId(Long stepId) {
        Image image = imageRepository.findByStepId(stepId);
        if(image != null){
            return convertEntityToDTO(image);
        }else{
            throw new RuntimeException("Image not found");
        }
    }

    private ImageDTO convertEntityToDTO(Image image){

        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());

        //decompress the compressed byte and set it to image DTO:
        try{
        imageDTO.setUploaded(ImageUtils.decompressImage(image.getUploaded()));
        }catch (DataFormatException e){
            e.printStackTrace();
        }catch(IOException e){
            throw new RuntimeException("Could not decompress image");
        }

        //type image
        imageDTO.setType(image.getType());

        imageDTO.setLinked(image.getLinked());

        //checking who it is related to and saving that id for front end use:
        if(image.getUser() != null){
            Long userId = image.getUser().getId();
            imageDTO.setRelationId(userId);
        }
        if(image.getBook() != null){
            Long id = image.getBook().getId();
            imageDTO.setRelationId(id);
        }
        if(image.getPage() != null){
            Long id = image.getPage().getId();
            imageDTO.setRelationId(id);
        }
        if(image.getStep() != null){
            Long stepId = image.getStep().getId();
            Recipe_Step step = recipeStepRepository.findById(stepId)
                    .orElseThrow(()-> new RuntimeException("Step not found"));
            Long recipePageId = step.getRecipePage().getId();
            imageDTO.setRelationId(recipePageId);
        }

        return imageDTO;
    }

}
