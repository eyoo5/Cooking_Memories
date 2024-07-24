package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.ImageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipeBookRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.UserRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.ImageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
CRUD endpoints for recipe books
*/

@Controller
public class RecipeBookController {
    private final UserService userService;
    private final RecipeBookService recipeBookService;
    private final RecipePageService recipePageService;
    private final ImageService imageService;

    @Autowired
    public RecipeBookController(UserService userService,
                                RecipeBookService recipeBookService,
                                RecipePageService recipePageService, UserRepository userRepository,
                                RecipeBookRepository recipeBookRepository,
                                ImageService imageService) {
        this.userService = userService;
        this.recipeBookService = recipeBookService;
        this.recipePageService = recipePageService;
        this.imageService = imageService;
    }

    //get single book
    @GetMapping("/book/{bookId}")
    public String getBook (@PathVariable Long bookId, @RequestParam(defaultValue ="0") int page,
                           @RequestParam(defaultValue = "12") int size,
                           Model model){
        //Get 1 Book
        RecipeBookDTO book = recipeBookService.findRecipeBookById(bookId);
        model.addAttribute("recipeBook", book);

        if(!book.getPages().isEmpty()){
            Pageable pageable = PageRequest.of(page,size);
//        List<RecipePageDTO> recipePages = recipePageService.getAllRecipePagesByBook(bookId);
            Page<RecipePageDTO> recipePages = recipePageService.findAllRecipePagesByBook(bookId,pageable);
        model.addAttribute("recipePages", recipePages);
        }

        if(book.getImageId() != null){
            ImageDTO image = imageService.getImageById(book.getImageId());
            model.addAttribute("image", image);
        }else{
            model.addAttribute("image", new ImageDTO());
        }

        return "/fragments/singleBook";
    }


// Getting all books through pagination:
    @GetMapping("/books")
    public String getAllBooks(
            @RequestParam(defaultValue ="0") int page,
            @RequestParam(defaultValue = "12") int size,
            Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()){
            Pageable pageable = PageRequest.of(page,size);
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserDTO user = userService.findByEmail(email);
            Page <RecipeBookDTO> recipeBooks = recipeBookService.findAllRecipeBooks(user.getId(),pageable);
            model.addAttribute("recipeBooks", recipeBooks);
            return "/fragments/allBooks";
        }else{
            return "redirect:/error/404";
        }
    }

    //create new book:
    @GetMapping("/book/createBook")
    public String createBook(Model model){
        RecipeBookDTO recipeBook = new RecipeBookDTO();
        model.addAttribute("recipeBook", recipeBook);
        return "/fragments/createBook";
    }


    //edit book view:
    @GetMapping("/book/update/{bookId}")
    public String updateBook(@PathVariable Long bookId, Model model){
        //Get 1 Book
        RecipeBookDTO book = recipeBookService.findRecipeBookById(bookId);
        model.addAttribute("recipeBook", book);
        return "/fragments/editBook";
    }

    //Save created book:
    @PostMapping("/book/save")
    public String saveBook(@Validated @ModelAttribute("recipeBook") RecipeBookDTO recipeBook, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("recipeBook", recipeBook);
            return "redirect:/book/createBook?error";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Get authenticated user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();

            UserDTO user = userService.findByEmail(email);
            RecipeBookDTO savedBook = recipeBookService.saveRecipeBook(user.getId(),recipeBook);
            return "redirect:/book/" + savedBook.getId();
        } else {
            return "redirect:/book/creatBook?error";
        }
    }

    //updating book:
    @PostMapping("/book/updateBook/save")
    public String updateBook(@Validated @ModelAttribute("recipeBook") RecipeBookDTO recipeBook, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("recipeBook", recipeBook);
            return "redirect:/book/editBook?error";
        }

        RecipeBookDTO savedBook = recipeBookService.updateRecipeBook(recipeBook);
        return "redirect:/book/" + recipeBook.getId();
    }

    //delete book
    @GetMapping("/book/delete/{bookId}")
    public String deleteBook(@PathVariable Long bookId){
            RecipeBookDTO recipeBook = recipeBookService.findRecipeBookById(bookId);
            if(recipeBook != null){
            recipeBookService.deleteRecipeBook(bookId);
            return"/fragments/deleted";
            }else{
                return "redirect:/error/404";
            }
    }

}
