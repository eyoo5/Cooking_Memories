package com.estheryoo.yoo_esther_cookingmemories_casestudy.service.implentation;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.ImageRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipeBookRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipePageRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.UserRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipePageServiceImpl implements RecipePageService {
    private final RecipePageRepository recipePageRepository;
    private final UserRepository userRepository;
    private final RecipeBookRepository recipeBookRepository;

    @Autowired
    public RecipePageServiceImpl(RecipePageRepository recipePageRepository,
                                  UserRepository userRepository,
                                  ImageRepository imageRepository, RecipeBookRepository recipeBookRepository) {
        this.recipePageRepository = recipePageRepository;
        this.userRepository = userRepository;
        this.recipeBookRepository = recipeBookRepository;
    }

    //save new recipe to user
    @Override
    public void saveRecipePage(Long userId, RecipePageDTO recipePageDTO){
        Recipe_Page recipePage = new Recipe_Page();
        recipePage.setTitle(recipePageDTO.getTitle());

        if(recipePageDTO.hasDescription()){
            recipePage.setDescription(recipePageDTO.getDescription());
        }

        if(recipePageDTO.hasVideoLink()){
            recipePage.setVideoLink(recipePageDTO.getVideoLink());
        }

        Recipe_Page savedPage = recipePageRepository.save(recipePage);
        // adding user to saved book
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        savedPage.setUser(user);
    }

    //save one page to a book
    @Override
    @Transactional
    public void savePageToBook(Long bookId, RecipePageDTO recipePageDTO){

        // Fetch recipe book and page entities
        Recipe_Book recipeBook = recipeBookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Recipe Book not found with id: " + bookId));

        Recipe_Page recipePage = recipePageRepository.findById(recipePageDTO.getId())
                .orElseThrow(() -> new RuntimeException("Recipe Page not found with id: " + recipePageDTO.getId()));

        // Add page to book and update relationships
        List<Recipe_Page> recipePages = recipeBook.getPages();
        recipePages.add(recipePage);
        recipeBook.setPages(recipePages);

        // Save the updated book entity
        recipeBookRepository.save(recipeBook);
    }

    //save multiple pages to a book
    @Override
    @Transactional
    public void savePagesToBook(Long bookId, List<RecipePageDTO> recipePageDTOs){
        Recipe_Book recipeBook = recipeBookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Recipe Book not found with id: " + bookId));
        List<Recipe_Page> recipePages = recipeBook.getPages();
        for(RecipePageDTO recipePageDTO : recipePageDTOs){
            Recipe_Page recipePage = recipePageRepository.findById(recipePageDTO.getId())
                    .orElseThrow(()-> new RuntimeException("Recipe Page not found with id: " + recipePageDTO.getId()));
            recipePages.add(recipePage);
        }
        recipeBook.setPages(recipePages);
        recipeBookRepository.save(recipeBook);
    }

    //update Recipe page
    @Override
    public void updateRecipePage(Long userId, RecipePageDTO recipePageDTO){
        Recipe_Page recipePage = recipePageRepository.findByTitle(recipePageDTO.getTitle());
        if(recipePage != null){
            recipePage.setTitle(recipePageDTO.getTitle());
            if(recipePageDTO.hasDescription()){
                recipePage.setDescription(recipePageDTO.getDescription());
            }
            if(recipePageDTO.hasVideoLink()){
                recipePage.setVideoLink(recipePageDTO.getVideoLink());
            }
          recipePageRepository.save(recipePage);
        }else{
            throw new RuntimeException("Recipe Page not found with id: " + recipePageDTO.getId());
        }
    }

    @Override
    public void deleteRecipePage(Long userId, RecipePageDTO recipePageDTO){
        Recipe_Page page = recipePageRepository.findById(recipePageDTO.getId())
                .orElseThrow(() -> new RuntimeException("Recipe step not found"));
        // Remove the RecipeStep entity
        recipePageRepository.delete(page);
    }

    @Override
    public RecipePageDTO findRecipePageByTitle(String title) {
        Recipe_Page recipePage = recipePageRepository.findByTitle(title);
        return convertEntityToDTO(recipePage);
    }

    @Override
    public RecipePageDTO findRecipePageById(Long id) {
        Recipe_Page recipePage = recipePageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe Page not found with id: " + id));
        return convertEntityToDTO(recipePage);
    }

    @Override
    public List <RecipePageDTO> getAllRecipePagesByUser(Long id) {
        List<Recipe_Page> recipePages = userRepository.findById(id).get().getPages();
        return recipePages.stream().map(this:: convertEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List <RecipePageDTO> getAllRecipePagesByBook(Long bookId) {
        List<Recipe_Page> recipePages = userRepository.findById(bookId).get().getPages();
        return recipePages.stream().map(this:: convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private RecipePageDTO convertEntityToDTO(Recipe_Page recipePage){
        RecipePageDTO pageDTO = new RecipePageDTO();

        pageDTO.setTitle(recipePage.getTitle());
        pageDTO.setCreatedAt(recipePage.getCreatedAt().toString());
        pageDTO.setIngredients(recipePage.getIngredients());
        pageDTO.setDescription(recipePage.getDescription());
        pageDTO.setVideoLink(recipePage.getVideoLink());

       List <String> steps = recipePage.getSteps().stream()
               .map(Recipe_Step::getDescription)
               .collect(Collectors.toList());
        pageDTO.setSteps(steps);
        return pageDTO;
    }
}
