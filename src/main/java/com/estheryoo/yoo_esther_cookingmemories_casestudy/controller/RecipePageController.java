package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeStepDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeStepService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RecipePageController {
    private final UserService userService;
    private final RecipeBookService recipeBookService;
    private final RecipePageService recipePageService;
    private final RecipeStepService recipeStepService;

    @Autowired
    public RecipePageController(UserService userService,
                                RecipeBookService recipeBookService,
                                RecipePageService recipePageService,
                                RecipeStepService recipeStepService) {
        this.userService = userService;
        this.recipeBookService = recipeBookService;
        this.recipePageService = recipePageService;
        this.recipeStepService = recipeStepService;
    }

    //get single recipe page
    @GetMapping("/recipe/{pageId}")
    public String getRecipePage(@PathVariable Long pageId, Model model) {
        RecipePageDTO recipePage = recipePageService.findRecipePageById(pageId);
        List<RecipeBookDTO> recipeBooks = recipeBookService.getAllRecipeBooksByPageId(pageId);
        List <RecipeStepDTO> recipeSteps = recipeStepService.getRecipeStepsByPageId(pageId);

        model.addAttribute("recipePage", recipePage);
        model.addAttribute("recipeBooks", recipeBooks);
        model.addAttribute("recipeSteps", recipeSteps);
        return "/fragments/singlePage";
    }

    @GetMapping("/recipes")
    public String getAllPages( Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null && auth.isAuthenticated()){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String email = userDetails.getUsername();

            UserDTO user = userService.findByEmail(email);
            List <RecipePageDTO> recipePages = recipePageService.getAllRecipePagesByUser(user.getId());
            model.addAttribute("recipePages", recipePages);
            return "/fragments/allPages";
        }else{
            return "redirect:/error/404";
        }
    }

    @GetMapping("/recipe/createRecipe")
    public String createRecipe(Model model){
        RecipePageDTO recipePage = new RecipePageDTO();
        model.addAttribute("recipePage", recipePage);
        return "/fragments/createPage";
    }

    @PostMapping("/recipe/save")
    public String saveBook(@ModelAttribute("recipePage") RecipePageDTO recipePage, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("recipePage", recipePage);
            return "redirect:/book/createBook?error";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // Get authenticated user details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            UserDTO user = userService.findByEmail(email);

            System.out.print(recipePage.getIngredients());

            RecipePageDTO savedPage = recipePageService.saveRecipePage(user.getId(),recipePage);
            return "redirect:/recipe/" + savedPage.getId();
        } else {
            return "redirect:/recipe/creatPage?error";
        }
    }

}
