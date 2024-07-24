package com.estheryoo.yoo_esther_cookingmemories_casestudy.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.util.List;


/*
Data Transfer Object for Recipe Book
*/

@Getter
@Setter
@NoArgsConstructor

public class RecipeBookDTO {

    private Long id;

    private String createdAt;

    @NotEmpty(message="You must provide a title for your book.")
    private String title;

    private Long imageId;

    private String description;

    private List<String> pages;

    public boolean hasDescription(){
        return this.description != null;
    }

    public boolean hasPages(){
        return this.pages != null;
    }

    @Override
    public String toString() {
        return "RecipeBookDTO{" +
                "id=" + id +
                ", createdAt='" + createdAt + '\'' +
                ", title='" + title + '\'' +
                ", imageId=" + imageId +
                ", description='" + description + '\'' +
                ", pages=" + pages +
                '}';
    }
}
