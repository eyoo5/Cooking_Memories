package com.estheryoo.yoo_esther_cookingmemories_casestudy.service.implentation;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Image;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Book;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Page;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.User;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.ImageRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipeBookRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipePageRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.UserRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

/*Stores, deletes, and updates books*/

@Service
public class RecipeBookServiceImpl implements RecipeBookService {

    private final RecipeBookRepository recipeBookRepository;
    private final RecipePageRepository recipePageRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipeBookServiceImpl(RecipeBookRepository recipeBookRepository,
                                 RecipePageRepository recipePageRepository,
                                 ImageRepository imageRepository,
                                 UserRepository userRepository) {
        this.recipeBookRepository = recipeBookRepository;
        this.recipePageRepository = recipePageRepository;
        this.userRepository = userRepository;
    }

    //save new book
    @Override
    public void saveRecipeBook(Long userId, RecipeBookDTO recipeBookDTO){
        Recipe_Book recipeBook = new Recipe_Book();
        recipeBook.setTitle(recipeBookDTO.getTitle());

        if(recipeBookDTO.hasDescription()){
            recipeBook.setDescription(recipeBookDTO.getDescription());
        }

        //converts list of Strings to Entities
        if(recipeBookDTO.hasPages()){
            List<Recipe_Page> recipePages = new ArrayList<>();

            for(String pageTitle: recipeBookDTO.getPages()){
                Recipe_Page recipePageEntity = recipePageRepository.findByTitle(pageTitle);
                recipePages.add(recipePageEntity);
            }

            recipeBook.setPages(recipePages);
        }

        Recipe_Book savedBook = recipeBookRepository.save(recipeBook);
        // adding user to saved book
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        savedBook.setUser(user);
    }

    @Override
    public Recipe_Book findRecipeBookByTitle(String title){
        return recipeBookRepository.findByTitle(title);
    }

    @Override
    public List<RecipeBookDTO> getAllRecipeBooks(){
        List<Recipe_Book> recipeBooks = recipeBookRepository.findAll();
        return recipeBooks.stream().map(this:: convertEntityToDTO).collect(Collectors.toList());
    }

    private RecipeBookDTO convertEntityToDTO(Recipe_Book recipeBook){
        RecipeBookDTO bookDTO = new RecipeBookDTO();
        ;
        bookDTO.setTitle(recipeBook.getTitle());
        bookDTO.setDescription(recipeBook.getDescription());
        bookDTO.setCreatedAt(recipeBook.getCreatedAt().toString());
        bookDTO.setImage(recipeBook.getImage().getUploaded());

        if(recipeBook.getImage() != null){
            byte[] byteArray = recipeBook.getImage().getUploaded();
            bookDTO.setImage(byteArray);
        }
        List <String> pages = recipeBook.getPages().stream().map(Recipe_Page::getTitle).collect(Collectors.toList());
        return bookDTO;
    }


}
