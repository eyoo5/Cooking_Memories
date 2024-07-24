package com.estheryoo.yoo_esther_cookingmemories_casestudy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

/*
Data Transfer Object for Step Information.
*/

public class RecipeStepDTO {
    private Long id;
    private String subtitle;
    private String description;
    private Long pageId;

    public boolean hasSubtitle(){
        return subtitle != null && !subtitle.isEmpty();
    }
    public boolean hasDescription(){
        return description != null && !description.isEmpty();
    }

    public boolean hasSubtitle(String subtitle){
        return subtitle != null && !subtitle.isEmpty();
    }
    public boolean hasDescription(String description){
        return description != null && !description.isEmpty();
    }

    @Override
    public String toString() {
        return "RecipeStepDTO{" +
                "id=" + id +
                ", subtitle='" + subtitle + '\'' +
                ", description='" + description + '\'' +
                ", pageId=" + pageId +
                '}';
    }
}
