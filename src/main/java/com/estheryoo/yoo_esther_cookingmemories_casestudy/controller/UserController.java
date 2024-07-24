package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.ImageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.security.CustomUserDetailsService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.ImageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/*
CRUD endpoints for user
*/

@Controller
public class UserController {
    private final UserService userService;
    private final RecipeBookService recipeBookService;
    private final RecipePageService recipePageService;
    private final ImageService imageService;

    @Autowired
    public UserController(UserService userService,
                          RecipeBookService recipeBookService,
                          RecipePageService recipePageService,
                          ImageService imageService,
                          CustomUserDetailsService customUserDetailsService){
        this.userService = userService;
        this.recipeBookService = recipeBookService;
        this.recipePageService = recipePageService;
        this.imageService = imageService;
    }


    @GetMapping("/user")
    public String getUser (
            @RequestParam(name="page", defaultValue ="0") int page,
            @RequestParam(name= "size" , defaultValue = "4") int size,
            Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Pageable pageable = PageRequest.of(page,size);
            // Get authenticated user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();

            //finding user
            UserDTO user = userService.findByEmail(email);

            //Find All Books By User
            Page<RecipeBookDTO> recipeBooks = recipeBookService.findAllRecipeBooks(user.getId(),pageable);
            //Find All Recipes By User
            Page<RecipePageDTO> recipePages = recipePageService.findAllRecipePagesByUser(user.getId(),pageable);

            //verifying if there is an associated image. If so, it will find it and attach the information.
            if(user.hasImageId()){
            ImageDTO image = imageService.getImageById(user.getImageId());
                model.addAttribute("image", image);
            } else{
                ImageDTO image = new ImageDTO();
                model.addAttribute("image", image);
            }

            model.addAttribute("user", user);
            model.addAttribute("recipeBooks", recipeBooks);
            model.addAttribute("recipePages", recipePages);
//            model.addAttribute("image", image);
            return "/fragments/user";

        } else {
            return "redirect:/login?error";
        }
    }


    @GetMapping("/register")
    public String showRegistrationForm (Model model){
        UserDTO user = new UserDTO();
        model.addAttribute("user",user);
        return "/fragments/signUp";
    }

    @PostMapping("/register/save")
    public String saveUser (@Valid @ModelAttribute("user")UserDTO user, BindingResult result, Model model){
        UserDTO existing = userService.findByEmail(user.getEmail());

        if(existing != null){
            result.rejectValue("email", "email.exists", "This email already exists");
        }
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "/fragments/signUp";
        }
//        UserDTO savedUser = userService.saveUser(user);
//        redirectAttributes.addAttribute("userId", savedUser.getId());
//        return "redirect:/user/{userId}";
        userService.saveUser(user);
        return "redirect:/register?success";
    }


}
