package com.estheryoo.yoo_esther_cookingmemories_casestudy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor


public class RecipeStepDTO {
    private Long id;
    private String subtitle;
    private String description;

    private byte[] image;

    public boolean hasSubtitle(){
        return subtitle != null && !subtitle.isEmpty();
    }
    public boolean hasDescription(){
        return description != null && !description.isEmpty();
    }

    public boolean hasImage(){
        return image != null;
    }

}
