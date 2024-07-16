package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.User;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        List<RecipePageDTO> recipePages = recipePageService.getAllRecipePages(userId);

        model.addAttribute("user", user);
        model.addAttribute("recipeBooks", recipeBooks);
        model.addAttribute("recipePages", recipePages);

        return "/fragments/user";
    }
}
