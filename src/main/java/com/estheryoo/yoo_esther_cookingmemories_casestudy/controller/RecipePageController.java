package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeStepDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeStepService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RecipePageController {
    private final UserService userService;
    private final RecipeBookService recipeBookService;
    private final RecipePageService recipePageService;
    private final RecipeStepService recipeStepService;

    @Autowired
    public RecipePageController(UserService userService,
                                RecipeBookService recipeBookService,
                                RecipePageService recipePageService,
                                RecipeStepService recipeStepService) {
        this.userService = userService;
        this.recipeBookService = recipeBookService;
        this.recipePageService = recipePageService;
        this.recipeStepService = recipeStepService;
    }

    //get single recipe page
    @GetMapping("/recipe/{pageId}")
    public String getRecipePage(@PathVariable Long pageId, Model model) {
        RecipePageDTO recipePage = recipePageService.findRecipePageById(pageId);
        List<RecipeBookDTO> recipeBooks = recipeBookService.getAllRecipeBooksByPageId(pageId);
        List <RecipeStepDTO> recipeSteps = recipeStepService.getRecipeStepsByPageId(pageId);

        model.addAttribute("recipePage", recipePage);
        model.addAttribute("recipeBooks", recipeBooks);
        model.addAttribute("recipeSteps", recipeSteps);
        return "/fragments/singlePage";
    }

    @GetMapping("/recipes/{userId}")
    public String getAllPages(@PathVariable Long userId, Model model) {
        List <RecipePageDTO> recipePages = recipePageService.getAllRecipePagesByUser(userId);
        model.addAttribute("recipePages", recipePages);
        return "/fragments/allPages";
    }


}
