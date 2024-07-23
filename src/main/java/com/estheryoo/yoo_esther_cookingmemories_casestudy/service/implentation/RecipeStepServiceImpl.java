package com.estheryoo.yoo_esther_cookingmemories_casestudy.service.implentation;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeBookDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.RecipeStepDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Book;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Page;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Step;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.ImageRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipePageRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RecipeStepRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.RecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeStepServiceImpl implements RecipeStepService {
    private final RecipeStepRepository recipeStepRepository;
    private final RecipePageRepository recipePageRepository;

    @Autowired
    public RecipeStepServiceImpl(RecipeStepRepository recipeStepRepository,
                                 RecipePageRepository recipePageRepository,
                                 ImageRepository imageRepository) {
        this.recipeStepRepository = recipeStepRepository;
        this.recipePageRepository = recipePageRepository;
    }

    @Override
    public RecipeStepDTO saveRecipeStep(Long pageId, RecipeStepDTO recipeStepDTO){
        Recipe_Step recipeStep = new Recipe_Step();
        //setting user
        Recipe_Page recipePage = recipePageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Page not found"));

        recipeStep.setRecipePage(recipePage);
        if(recipeStepDTO.hasSubtitle()){
            recipeStep.setSubtitle(recipeStepDTO.getSubtitle());
        }

        if(recipeStepDTO.hasDescription()){
            recipeStep.setDescription(recipeStepDTO.getDescription());
        }

        Recipe_Step savedStep = recipeStepRepository.save(recipeStep);
        return convertEntityToDTO(savedStep);
    }

    @Override
    public void saveRecipeStepToPage(String pageTitle, RecipeStepDTO recipeStepDTO){
        Recipe_Step recipeStep = new Recipe_Step();

        if(recipeStepDTO.hasSubtitle()){
            recipeStep.setSubtitle(recipeStepDTO.getSubtitle());
        }

        if(recipeStepDTO.hasDescription()){
            recipeStep.setDescription(recipeStepDTO.getDescription());
        }

        Recipe_Step savedStep = recipeStepRepository.save(recipeStep);
        Recipe_Page page = recipePageRepository.findByTitle(pageTitle);

        List<Recipe_Step> steps = page.getSteps();
        steps.add(savedStep);
        page.setSteps(steps);

        //save the updated page
        recipePageRepository.save(page);
    }

    @Override
    public void updateRecipeStep( RecipeStepDTO recipeStepDTO){
            Recipe_Step recipeStep = recipeStepRepository.findById(recipeStepDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Recipe step not found"));

            if(recipeStep != null){
                if (recipeStepDTO.hasSubtitle()) {
                    recipeStep.setSubtitle(recipeStepDTO.getSubtitle());
                }

                if (recipeStepDTO.hasDescription()) {
                    recipeStep.setDescription(recipeStepDTO.getDescription());
                }
                recipeStepRepository.save(recipeStep);
        }else{
                throw new RuntimeException("Recipe step not found in update.");
            }
    }

    @Override
    public void deleteRecipeStep(String pageTitle, RecipeStepDTO recipeStepDTO){
        Recipe_Step step = recipeStepRepository.findById(recipeStepDTO.getId()).orElseThrow(() -> new RuntimeException("Recipe step not found"));

        // Check if the RecipeStep belongs to the specified RecipePage
        if (!step.getRecipePage().getTitle().equals(pageTitle)) {
            throw new IllegalArgumentException("Recipe step does not belong to the specified page");
        }

        // Remove the RecipeStep entity
        recipeStepRepository.delete(step);
    }

    //get single recipe by id
    @Override
    public RecipeStepDTO getRecipeStepById(Long id){
        Recipe_Step recipeStep = recipeStepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe step not found"));
        return convertEntityToDTO(recipeStep);
    }

    @Override
    public List<RecipeStepDTO> getRecipeStepsByPageId(Long pageId){
        List <Recipe_Step> steps = recipeStepRepository.findByPage_Id(pageId);
       return steps.stream().map(this:: convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List <RecipeStepDTO> getRecipeSteps (String pageTitle){
        Recipe_Page page = recipePageRepository.findByTitle(pageTitle);
        List<Recipe_Step> steps = page.getSteps();
        List<RecipeStepDTO> stepDTOs = new ArrayList<>();
        for(Recipe_Step step : steps){
            RecipeStepDTO stepDTO = new RecipeStepDTO();
            stepDTO.setId(step.getId());
            stepDTO.setDescription(step.getDescription());
            stepDTO.setSubtitle(step.getSubtitle());
            stepDTOs.add(stepDTO);
        }
        return stepDTOs;
    }

    private RecipeStepDTO convertEntityToDTO(Recipe_Step recipeStep){
        RecipeStepDTO stepDTO = new RecipeStepDTO();
        stepDTO.setId(recipeStep.getId());
        stepDTO.setDescription(recipeStep.getDescription());
        stepDTO.setSubtitle(recipeStep.getSubtitle());
        stepDTO.setPageId(recipeStep.getRecipePage().getId());
        return stepDTO;
    }
}
