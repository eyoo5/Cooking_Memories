package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RecipeBookController {
    private final RecipeBookService recipeBookService;

    @Autowired
    public RecipeBookController(RecipeBookService recipeBookService) {
        this.recipeBookService = recipeBookService;
    }


}
