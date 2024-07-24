package com.estheryoo.yoo_esther_cookingmemories_casestudy.service.implentation;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.*;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
Methods connect the repository to find recipe page information and
converts this into a recipe page DTO (Data Transfer Object)
It performs CRUD operations for recipe pages.
*/

@Service
public class RecipePageServiceImpl implements RecipePageService {
    private final RecipePageRepository recipePageRepository;
    private final UserRepository userRepository;
    private final RecipeBookRepository recipeBookRepository;
    private final RecipeStepRepository recipeStepRepository;

    @Autowired
    public RecipePageServiceImpl(RecipePageRepository recipePageRepository,
                                 UserRepository userRepository,
                                 ImageRepository imageRepository,
                                 RecipeBookRepository recipeBookRepository,
                                 RecipeStepRepository recipeStepRepository) {
        this.recipePageRepository = recipePageRepository;
        this.userRepository = userRepository;
        this.recipeBookRepository = recipeBookRepository;
        this.recipeStepRepository = recipeStepRepository;
    }

    //save new recipe to user
    @Override
    public RecipePageDTO saveRecipePage(Long userId, RecipePageDTO recipePageDTO){
        Recipe_Page recipePage = new Recipe_Page();
        recipePage.setTitle(recipePageDTO.getTitle());

        if(recipePageDTO.hasDescription()){
            recipePage.setDescription(recipePageDTO.getDescription());
        }

        if(recipePageDTO.hasVideoLink()){
            recipePage.setVideoLink(recipePageDTO.getVideoLink());
        }

        if(recipePageDTO.hasIngredients()){
            recipePage.setIngredients(recipePageDTO.getIngredients());
        }

        // adding user to saved page
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        recipePage.setUser(user);
        Recipe_Page savedPage = recipePageRepository.save(recipePage);
        return convertEntityToDTO(savedPage);
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
    public void updateRecipePage( RecipePageDTO recipePageDTO){
        Recipe_Page recipePage = recipePageRepository.findById(recipePageDTO.getId())
                .orElseThrow(()-> new RuntimeException("Recipe Page not found with id: " + recipePageDTO.getId()));
        if(recipePage != null){
            recipePage.setTitle(recipePageDTO.getTitle());

            if(recipePageDTO.hasDescription()){
                recipePage.setDescription(recipePageDTO.getDescription());
            }

            if(recipePageDTO.hasVideoLink()){
                recipePage.setVideoLink(recipePageDTO.getVideoLink());
            }

            if(recipePageDTO.hasIngredients()){
                recipePage.setIngredients(recipePageDTO.getIngredients());
            }
          recipePageRepository.save(recipePage);
        }else{
            throw new RuntimeException("Recipe Page not found with id: " + recipePageDTO.getId());
        }
    }

    @Override
    public void deleteRecipePage(Long recipePageId){
        Recipe_Page page = recipePageRepository.findById(recipePageId)
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
    public RecipePageDTO findRecipePageByStepId(Long id){
        Recipe_Step step = recipeStepRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Recipe Step not found with id: " + id));
        Recipe_Page recipePage = step.getRecipePage();
        return convertEntityToDTO(recipePage);
    }

    @Override
    public List <RecipePageDTO> getAllRecipePagesByUser(Long id) {
        List<Recipe_Page> recipePages = userRepository.findById(id).get().getPages();
        return recipePages.stream().map(this:: convertEntityToDTO)
                .collect(Collectors.toList());
    }

    //using pagination to retrieve pages in sets to display by user
    @Override
    public Page<RecipePageDTO> findAllRecipePagesByUser(Long id, Pageable pageable) {
        Page <Recipe_Page> recipePages = recipePageRepository.findByUserId(id,pageable);
        return recipePages.map(this::convertEntityToDTO);
    }

    //using pagination to retrieve pages in sets to display by book association
    @Override
    public Page<RecipePageDTO> findAllRecipePagesByBook(Long id, Pageable pageable) {
        Page <Recipe_Page> recipePages = recipePageRepository.findByBookId(id,pageable);
        return recipePages.map(this::convertEntityToDTO);
    }

    @Override
    public List <RecipePageDTO> getAllRecipePagesByBook(Long bookId) {
        Recipe_Book book = recipeBookRepository.findById(bookId)
                .orElseThrow(()-> new RuntimeException("Book not found with id in getAllRecipePagesByBook method: " + bookId));
        if(book.getPages().size() > 1){
            List<Recipe_Page> recipePages = userRepository.findById(bookId).get().getPages();
            return recipePages.stream().map(this:: convertEntityToDTO)
                .collect(Collectors.toList());
        }
            return Collections.emptyList();
    }

    private RecipePageDTO convertEntityToDTO(Recipe_Page recipePage){
        RecipePageDTO pageDTO = new RecipePageDTO();
        pageDTO.setId(recipePage.getId());
        pageDTO.setTitle(recipePage.getTitle());
        pageDTO.setCreatedAt(recipePage.getCreatedAt().toString());
        pageDTO.setIngredients(recipePage.getIngredients());
        pageDTO.setDescription(recipePage.getDescription());

        if(recipePage.getImage() != null){
            Image image = recipePage.getImage();
            pageDTO.setImageId(image.getId());
        }

        if(recipePage.getVideoLink() != null){
            pageDTO.setVideoLink(recipePage.getVideoLink());
        }

        if(recipePage.getSteps().size() > 1){
        List <String> steps = recipePage.getSteps().stream()
               .map(Recipe_Step::getDescription)
               .collect(Collectors.toList());

        pageDTO.setSteps(steps);
        }else{
            pageDTO.setSteps(null);
        }
        return pageDTO;
    }
}
