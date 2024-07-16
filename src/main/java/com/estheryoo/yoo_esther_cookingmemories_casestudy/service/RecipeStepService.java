package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeStepDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecipeStepService {
    void saveRecipeStepToPage(String pageTitle, RecipeStepDTO recipeStepDTO);
    void updateRecipeStep(RecipeStepDTO recipeStepDTO);
    void deleteRecipeStep(String pageTitle, RecipeStepDTO recipeStepDTO);
    List<RecipeStepDTO> getRecipeSteps(String pageTitle);
}
