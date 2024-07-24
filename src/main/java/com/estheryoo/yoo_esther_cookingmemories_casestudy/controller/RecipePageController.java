package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
CRUD endpoints for recipe pages
*/

@Controller
public class RecipePageController {
    private final UserService userService;
    private final RecipeBookService recipeBookService;
    private final RecipePageService recipePageService;
    private final RecipeStepService recipeStepService;
    private final ImageService imageService;

    @Autowired
    public RecipePageController(UserService userService,
                                RecipeBookService recipeBookService,
                                RecipePageService recipePageService,
                                RecipeStepService recipeStepService,
                                ImageService imageService) {
        this.userService = userService;
        this.recipeBookService = recipeBookService;
        this.recipePageService = recipePageService;
        this.recipeStepService = recipeStepService;
        this.imageService = imageService;
    }

    //get single recipe page
    @GetMapping("/recipe/{pageId}")
    public String getRecipePage(@PathVariable Long pageId,
                                @RequestParam(defaultValue ="0") int page,
                                @RequestParam(defaultValue = "12") int size,
                                Model model) {
        Pageable pageable = PageRequest.of(page,size);
        RecipePageDTO recipePage = recipePageService.findRecipePageById(pageId);
        Page<RecipeBookDTO> recipeBooks = recipeBookService.findAllRecipeBooksByPageId(pageId,pageable);
        List <RecipeStepDTO> recipeSteps = recipeStepService.getRecipeStepsByPageId(pageId);

        model.addAttribute("recipePage", recipePage);
        model.addAttribute("recipeBooks", recipeBooks);
        model.addAttribute("recipeSteps", recipeSteps);

        if(recipePage.getImageId() != null){
            ImageDTO image = imageService.getImageById(recipePage.getImageId());
            model.addAttribute("image", image);
        }else{
            model.addAttribute("image", new ImageDTO());
        }
        return "/fragments/singlePage";
    }

    //get all recipes by user
    @GetMapping("/recipes")
    public String getAllPages(
            @RequestParam(defaultValue ="0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()){
            Pageable pageable = PageRequest.of(page,size);
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserDTO user = userService.findByEmail(email);
            Page<RecipePageDTO> recipePages = recipePageService.findAllRecipePagesByUser(user.getId(),pageable);
            model.addAttribute("recipePages", recipePages);
            return "/fragments/allPages";
        }else{
            return "redirect:/error/404";
        }
    }

    //get create new recipe view
    @GetMapping("/recipe/createRecipe")
    public String createRecipe(Model model){
        RecipePageDTO recipePage = new RecipePageDTO();
        model.addAttribute("recipePage", recipePage);
        return "/fragments/createPage";
    }

    //get update recipe view
    @GetMapping("/recipe/update/{recipeId}")
    public String updateRecipe(@PathVariable Long recipeId, Model model){
        RecipePageDTO recipePage = recipePageService.findRecipePageById(recipeId);
        model.addAttribute("recipePage", recipePage);
        return "/fragments/editPage";
    }

    //save new recipe
    @PostMapping("/recipe/save")
    public String saveRecipe(@ModelAttribute("recipePage") RecipePageDTO recipePage, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("recipePage", recipePage);
            return "redirect:/book/createBook?error";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Get authenticated user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            UserDTO user = userService.findByEmail(email);

            RecipePageDTO savedPage = recipePageService.saveRecipePage(user.getId(),recipePage);
            return "redirect:/recipe/" + savedPage.getId();
        } else {
            return "redirect:/recipe/creatPage?error";
        }
    }

    //update recipe
    @PostMapping("/recipe/updateRecipe/save")
    public String updateRecipe(@ModelAttribute("recipePage") RecipePageDTO recipePage, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("recipePage", recipePage);
            return "redirect:/book/editPage?error";
        }
        recipePageService.updateRecipePage(recipePage);
        return "redirect:/recipe/" + recipePage.getId();
    }



    //delete recipe
    @GetMapping("/recipe/delete/{pageId}")
    public String deleteBook(@PathVariable Long pageId){
        RecipePageDTO recipePage = recipePageService.findRecipePageById(pageId);
        if(recipePage != null){
            recipePageService.deleteRecipePage(recipePage.getId());
            return"/fragments/deleted";
        }else{
            return "redirect:/error/404";
        }
    }

}
