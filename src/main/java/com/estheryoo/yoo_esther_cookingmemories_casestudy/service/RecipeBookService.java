package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecipeBookService {
    void saveRecipeBook(Long userId, RecipeBookDTO recipeBookDTO);
    Recipe_Book findRecipeBookByTitle(String title);
    List<RecipeBookDTO> getAllRecipeBooks();
}
