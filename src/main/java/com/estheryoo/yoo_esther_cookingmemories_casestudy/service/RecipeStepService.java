package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeStepDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecipeStepService {
    RecipeStepDTO saveRecipeStep(Long pageId,RecipeStepDTO recipeStepDTO);
    void saveRecipeStepToPage(String pageTitle, RecipeStepDTO recipeStepDTO);
    void updateRecipeStep(RecipeStepDTO recipeStepDTO);
    void deleteRecipeStep(String pageTitle, RecipeStepDTO recipeStepDTO);
    RecipeStepDTO getRecipeStepById(Long id);
    List<RecipeStepDTO> getRecipeSteps(String pageTitle);
    List <RecipeStepDTO> getRecipeStepsByPageId(Long pageId);
}
