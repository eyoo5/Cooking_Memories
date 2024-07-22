package com.estheryoo.yoo_esther_cookingmemories_casestudy.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class RecipePageDTO {
    private Long id;

    @NotEmpty (message="Please provide a title for your page.")
    private String title;

    private String createdAt;

    private String description;

    private String videoLink;

    private byte[] image;

    private List<String> ingredients;

    private List<String> steps;

    public boolean hasDescription(){
        return this.description != null;
    }
    public boolean hasVideoLink(){
        return this.videoLink != null;
    }
    public boolean hasIngredients(){
        return this.ingredients != null;
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }
    public void addIngredients(List<String> ingredients) {
        this.ingredients.addAll(ingredients);
    }

}
