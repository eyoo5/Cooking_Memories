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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

/*
Methods connect the repository to find recipe book information and
converts this into a user DTO (Data Transfer Object)
It performs CRUD operations for recipe books.
*/

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
    public RecipeBookDTO saveRecipeBook(Long userId, RecipeBookDTO recipeBookDTO){
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

        // adding user to saved book
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        recipeBook.setUser(user);

        Recipe_Book savedBook = recipeBookRepository.save(recipeBook);
        return convertEntityToDTO(savedBook);
    }

    @Override
    public RecipeBookDTO updateRecipeBook( RecipeBookDTO recipeBookDTO){
        Recipe_Book recipeBook = recipeBookRepository.findById(recipeBookDTO.getId())
                .orElseThrow(()-> new RuntimeException("Recipe book not found"));

        if(!recipeBookDTO.getTitle().equals( recipeBook.getTitle())){
            recipeBook.setTitle(recipeBookDTO.getTitle());
        }

        if(recipeBookDTO.hasDescription()){
            recipeBook.setDescription(recipeBookDTO.getDescription());
        }

        //converts list of Strings to Entities
        if(recipeBookDTO.hasPages()){
            List<Recipe_Page> recipePages = new ArrayList<>();
            for(String pageId: recipeBookDTO.getPages()){
                Long recipeId = Long.parseLong(pageId);
                Recipe_Page recipePageEntity = recipePageRepository.findById(recipeId)
                        .orElseThrow(()-> new RuntimeException("Recipe page not found in updateRecipeBook"));
                recipePages.add(recipePageEntity);
            }
            recipeBook.setPages(recipePages);
        }

        Recipe_Book savedBook = recipeBookRepository.save(recipeBook);
        return convertEntityToDTO(savedBook);
    }



    @Override
    public RecipeBookDTO findRecipeBookByTitle(String title){
        Recipe_Book book = recipeBookRepository.findByTitle(title);
        return convertEntityToDTO(book);
    }

    @Override
    public RecipeBookDTO findRecipeBookById(Long id){
        Recipe_Book book = recipeBookRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
        return convertEntityToDTO(book);
    }

    @Override
    public List<RecipeBookDTO> getAllRecipeBooks(Long userId){
        List<Recipe_Book> recipeBooks = userRepository.findById(userId).get().getBooks();
        return recipeBooks.stream().map(this:: convertEntityToDTO).collect(Collectors.toList());
    }

    //using pagination to retrieve books in sets to display
    @Override
    public Page<RecipeBookDTO> findAllRecipeBooks(Long userId, Pageable pageable){
        Page<Recipe_Book> recipeBooksPage = recipeBookRepository.findByUserId(userId,pageable);
        return recipeBooksPage.map(this:: convertEntityToDTO);
    }


    //using pagination to retrieve books associated to a page:
    @Override
    public Page<RecipeBookDTO> findAllRecipeBooksByPageId(Long id, Pageable pageable){
        Page<Recipe_Book> recipeBooksPage = recipeBookRepository.findByPageId(id,pageable);
        return recipeBooksPage.map(this:: convertEntityToDTO);
    }


    @Override
    public List <RecipeBookDTO> getAllRecipeBooksByPageId(Long pageId){
        List <Recipe_Book> recipeBooks = recipeBookRepository.findByPage_Id(pageId);
        return recipeBooks.stream().map(this:: convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteRecipeBook(Long bookId) {
        Recipe_Book book = recipeBookRepository.findById(bookId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        recipeBookRepository.delete(book);
    }


    private RecipeBookDTO convertEntityToDTO(Recipe_Book recipeBook){
        RecipeBookDTO bookDTO = new RecipeBookDTO();

        bookDTO.setId(recipeBook.getId());
        bookDTO.setTitle(recipeBook.getTitle());
        bookDTO.setDescription(recipeBook.getDescription());

        Date createdAt = new Date(recipeBook.getCreatedAt().getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = formatter.format(createdAt);
        bookDTO.setCreatedAt(formattedDate);

        if(recipeBook.getImage()!= null){
            Image image = recipeBook.getImage();
            bookDTO.setImageId(image.getId());
        }

        if(recipeBook.getPages() != null){
        List <String> pages = recipeBook.getPages().stream().map(Recipe_Page::getTitle).collect(Collectors.toList());
        bookDTO.setPages(pages);
        }else{
            bookDTO.setPages(null);
        }

        return bookDTO;
    }


}
