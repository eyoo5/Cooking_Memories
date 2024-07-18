package com.estheryoo.yoo_esther_cookingmemories_casestudy.dto;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Image;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Page;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class RecipeBookDTO {

    private Long id;

    private String createdAt;

    @NotEmpty(message="You must provide a title for your book.")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    private String description;

    private List<String> pages;

    public boolean hasDescription(){
        return this.description != null;
    }

    public boolean hasPages(){
        return this.pages != null;
    }

    public boolean hasImage(){
        return this.image != null;
    }
}
