package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecipePageService {
    RecipePageDTO saveRecipePage(Long userId, RecipePageDTO recipePageDTO);
    void savePageToBook(Long bookId, RecipePageDTO recipePageDTO);
    void savePagesToBook(Long bookId, List<RecipePageDTO> pages);
    void updateRecipePage(RecipePageDTO recipePageDTO);
    void deleteRecipePage(Long userId);
    RecipePageDTO findRecipePageByTitle(String title);
    RecipePageDTO findRecipePageById(Long id);
    List<RecipePageDTO> getAllRecipePagesByUser(Long id);
    Page<RecipePageDTO> findAllRecipePagesByUser(Long id, Pageable pageable);
    List<RecipePageDTO> getAllRecipePagesByBook(Long id);
    Page<RecipePageDTO> findAllRecipePagesByBook(Long id, Pageable pageable);
}
