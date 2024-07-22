package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.UserRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RecipeBookController {
    private final UserService userService;
    private final RecipeBookService recipeBookService;
    private final RecipePageService recipePageService;
    private final UserRepository userRepository;

    @Autowired
    public RecipeBookController(UserService userService,
                                RecipeBookService recipeBookService,
                                RecipePageService recipePageService, UserRepository userRepository) {
        this.userService = userService;
        this.recipeBookService = recipeBookService;
        this.recipePageService = recipePageService;
        this.userRepository = userRepository;
    }

    //get single book
    @GetMapping("/book/{bookId}")
    public String getBook (@PathVariable Long bookId, Model model){
        //Get 1 Book
        RecipeBookDTO book = recipeBookService.findRecipeBookById(bookId);
        model.addAttribute("recipeBook", book);

        if(book.hasPages()){
        List<RecipePageDTO> recipePages = recipePageService.getAllRecipePagesByBook(bookId);
        model.addAttribute("recipePages", recipePages);
        }
        return "/fragments/singleBook";
    }

    //get all books by user
    @GetMapping("/books")
    public String getAllBooks( Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserDTO user = userService.findByEmail(email);
            List <RecipeBookDTO> recipeBooks = recipeBookService.getAllRecipeBooks(user.getId());
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
