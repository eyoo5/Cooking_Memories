package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecipePageService {
    void saveRecipePage(Long userId, RecipePageDTO recipePageDTO);
    void savePageToBook(Long bookId, RecipePageDTO recipePageDTO);
    void savePagesToBook(Long bookId, List<RecipePageDTO> pages);
    void updateRecipePage(Long userId, RecipePageDTO recipePageDTO);
    void deleteRecipePage(Long userId, RecipePageDTO recipePageDTO);
    RecipePageDTO findRecipePageByTitle(String title);
    RecipePageDTO findRecipePageById(Long id);
    List<RecipePageDTO> getAllRecipePagesByUser(Long id);
    List<RecipePageDTO> getAllRecipePagesByBook(Long id);
}
