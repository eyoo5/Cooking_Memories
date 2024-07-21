package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RecipeBookController {
    private final UserService userService;
    private final RecipeBookService recipeBookService;
    private final RecipePageService recipePageService;

    @Autowired
    public RecipeBookController(UserService userService,
                                RecipeBookService recipeBookService,
                                RecipePageService recipePageService) {
        this.userService = userService;
        this.recipeBookService = recipeBookService;
        this.recipePageService = recipePageService;
    }

    //get single book
    @GetMapping("/book/{bookId}")
    public String getBook (@PathVariable Long bookId, Model model){
        //Get 1 Book
        RecipeBookDTO book = recipeBookService.findRecipeBookById(bookId);
        List<RecipePageDTO> recipePages = recipePageService.getAllRecipePagesByBook(bookId);
        model.addAttribute("recipeBook", book);
        model.addAttribute("recipePages", recipePages);
        return "/fragments/singleBook";
    }

    //get all books by user
    @GetMapping("/getAllBooks/{userId}")
    public String getAllBooks(@PathVariable Long userId, Model model){
        List <RecipeBookDTO> recipeBooks = recipeBookService.getAllRecipeBooks(userId);
        model.addAttribute("recipeBooks", recipeBooks);
        return "/fragments/allBooks";
    }

    @GetMapping("/book/createBook")
    public String createBook(Model model){
        RecipeBookDTO recipeBook = new RecipeBookDTO();
        model.addAttribute("recipeBook", recipeBook);
        return "/fragments/createBook";
    }

//    @PostMapping("/book/save")
//    public String saveBook(@ModelAttribute("recipeBook") RecipeBookDTO recipeBook, BindingResult result, Model model){
//        if(result.hasErrors()){
//            model.addAttribute("recipeBook", recipeBook);
//            return "/fragments/createBook";
//        }
//        recipeBookService.saveRecipeBook(recipeBook);
//        return "redirect:/book/" + recipeBook.getId();
//    }

}
