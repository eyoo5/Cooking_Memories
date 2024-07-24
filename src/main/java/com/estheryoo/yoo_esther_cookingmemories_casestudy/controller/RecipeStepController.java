package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipePageDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeStepDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeBookService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipePageService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeStepService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/*
CRUD endpoints for recipe steps
*/


@Controller
public class RecipeStepController {
    private final UserService userService;
    private final RecipeBookService recipeBookService;
    private final RecipePageService recipePageService;
    private final RecipeStepService recipeStepService;

    public RecipeStepController(UserService userService,
                                RecipeBookService recipeBookService,
                                RecipePageService recipePageService,
                                RecipeStepService recipeStepService) {
        this.userService = userService;
        this.recipeBookService = recipeBookService;
        this.recipePageService = recipePageService;
        this.recipeStepService = recipeStepService;
    }


    //Get update recipe step view
    @GetMapping("/step/{stepId}")
    public String getStep(@PathVariable Long stepId, Model model) {
        RecipeStepDTO recipeStep = recipeStepService.getRecipeStepById(stepId);
        model.addAttribute("recipeStep", recipeStep);
        return "/fragments/editStep";
    }

    //get create new step
    @GetMapping("/step/create/{pageId}")
    public String createRecipeStep(@PathVariable Long pageId, Model model) {
        RecipeStepDTO recipeStep = new RecipeStepDTO();
        RecipePageDTO recipePage = recipePageService.findRecipePageById(pageId);
        model.addAttribute("recipeStep", recipeStep);

        //find and send page to send to save end point.
        model.addAttribute("recipePage", recipePage);
        return "/fragments/createStep";
    }

    //update recipe step
    @PostMapping("/step/update")
    public String updateRecipeStep(@ModelAttribute("recipeStep") RecipeStepDTO recipeStep, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("recipeStep", recipeStep);
            return "redirect:/fragments/editStep?error";
        }
         recipeStepService.updateRecipeStep(recipeStep);
        RecipeStepDTO step = recipeStepService.getRecipeStepById(recipeStep.getId());
        return "redirect:/recipe/" + step.getPageId();

    }

    //Save new recipe step
    @PostMapping("/step/save/{pageId}")
    public String saveStep(@PathVariable Long pageId,
                           @ModelAttribute("recipeStep") RecipeStepDTO recipeStep,
                           BindingResult result,
                           Model model) {

        if(result.hasErrors()){
            model.addAttribute("recipeStep", recipeStep);
            return "redirect:/book/createBook?error";
        }

        RecipeStepDTO savedStep = recipeStepService.saveRecipeStep(pageId, recipeStep);
        return "redirect:/recipe/" + pageId;
    }



}
