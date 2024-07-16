package com.estheryoo.yoo_esther_cookingmemories_casestudy.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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

    @NotEmpty(message="Please add a list of ingredients.")
    private List<String> ingredients;

    private List<String> steps;

    public boolean hasDescription(){
        return this.description != null;
    }
    public boolean hasVideoLink(){
        return this.videoLink != null;
    }
}
