package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.ImageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.ImageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.OutputStream;


/*
upload image, update image, and get image  endpoints
*/

@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipePageService recipePageService;


    @Autowired
    public ImageController(ImageService imageService,
                           RecipePageService recipePageService) {
        this.imageService = imageService;
        this.recipePageService = recipePageService;
    }


    @GetMapping("/image/{imageId}")
    public void getImage(@PathVariable Long imageId, HttpServletResponse response) throws IOException {

        ImageDTO imageDTO = imageService.getImageById(imageId);

        if (imageDTO == null || imageDTO.getUploaded() == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] imageData = imageDTO.getUploaded();

        if("image/jpeg".equals(imageDTO.getType())) {
            response.setContentType("image/jpeg");
        }

        if("image/png".equals(imageDTO.getType())) {
            response.setContentType("image/png");
        }


        // Write image data to response output stream
        try (OutputStream out = response.getOutputStream()) {
            out.write(imageData);
        }catch(IOException e){
            e.printStackTrace();
        }

    }


    //save image to database. LinkedId is for the if statements below, the relationId is the database id for book, page, or step
    @PostMapping("/image/upload/{linkedId}/{relationId}")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @ModelAttribute("image") ImageDTO image,
                                   @PathVariable Long linkedId,
                                   @PathVariable Long relationId,
                                   BindingResult result,
                                   Model model){


        if(linkedId == 1){
            image.setLinked("user");

            try{
                imageService.uploadImage(file,image, relationId);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }

            return "redirect:/user";

        }
        if(linkedId == 2){
            image.setLinked("book");

            try{
                imageService.uploadImage(file,image, relationId);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }

            return "redirect:/book/" + relationId;
        }

        if(linkedId == 3){
            image.setLinked("page");
            try{
                imageService.uploadImage(file,image, relationId);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }

            return "redirect:/recipe/" + relationId;
        }

        if(linkedId == 4){
            image.setLinked("step");
            try{
                imageService.uploadImage(file,image, relationId);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }

            RecipePageDTO recipePage = recipePageService.findRecipePageByStepId(relationId);
            return "redirect:/recipe/" + recipePage.getId();
        }

        return "redirect:/user";
    }

}


