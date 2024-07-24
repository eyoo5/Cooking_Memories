package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;

/*
Methods that use recipe book repository queries to create, get, update, and delete a recipe book .
Recipe book entities are changed into a DTO (Data Transfer Object).
 */

@Component
public interface RecipeBookService {
    RecipeBookDTO saveRecipeBook(Long userId, RecipeBookDTO recipeBookDTO);
    RecipeBookDTO updateRecipeBook(RecipeBookDTO recipeBookDTO);
    void deleteRecipeBook(Long bookId);
    RecipeBookDTO findRecipeBookByTitle(String title);
    RecipeBookDTO findRecipeBookById(Long id);
    List<RecipeBookDTO> getAllRecipeBooks(Long userId);
    Page<RecipeBookDTO> findAllRecipeBooks(Long userId,Pageable pageable);
    List<RecipeBookDTO> getAllRecipeBooksByPageId(Long pageId);
    Page<RecipeBookDTO> findAllRecipeBooksByPageId(Long pageId,Pageable pageable);
}
