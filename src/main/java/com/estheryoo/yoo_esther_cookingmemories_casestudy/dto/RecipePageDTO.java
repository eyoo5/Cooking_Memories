package com.estheryoo.yoo_esther_cookingmemories_casestudy.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;


/*
Data Transfer Object for Recipe Page. Has methods to check if variables are not null.
*/

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

    private Long imageId;

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


    @Override
    public String toString() {
        return "RecipePageDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", description='" + description + '\'' +
                ", videoLink='" + videoLink + '\'' +
                ", imageId=" + imageId +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }
}
