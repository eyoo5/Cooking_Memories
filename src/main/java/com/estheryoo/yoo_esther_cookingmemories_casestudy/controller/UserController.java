package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.User;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final RecipeBookService recipeBookService;
    private final RecipePageService recipePageService;

    @Autowired
    public UserController(UserService userService,
                          RecipeBookService recipeBookService,
                          RecipePageService recipePageService){
        this.userService = userService;
        this.recipeBookService = recipeBookService;
        this.recipePageService = recipePageService;
    }

    @GetMapping("/user/{userId}")
    public String getUser (@PathVariable Long userId, Model model){
        //Get User
        UserDTO user = userService.findById(userId);
        List<RecipeBookDTO> recipeBooks = recipeBookService.getAllRecipeBooks(userId);
        List<RecipePageDTO> recipePages = recipePageService.getAllRecipePagesByUser(userId);

        model.addAttribute("user", user);
        model.addAttribute("recipeBooks", recipeBooks);
        model.addAttribute("recipePages", recipePages);
        return "/fragments/user";
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
