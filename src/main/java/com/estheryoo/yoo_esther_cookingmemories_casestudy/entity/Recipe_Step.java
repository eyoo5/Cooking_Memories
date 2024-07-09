package com.estheryoo.yoo_esther_cookingmemories_casestudy.entity;

import jakarta.persistence.*;
import lombok.*;

/* These are the recipe steps that have a Many to One relationship with Recipe Page*/


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "recipe_steps")
public class Recipe_Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //step name
    private String subtitle;

    @Column(nullable = false)
    private String description;

    @Lob
    @Column(nullable = true)
    private byte[] image;

    @ManyToOne
    @JoinColumn(name= "recipe_page_id", nullable = false)
    private Recipe_Page recipePage;

    public Recipe_Step(String description) {
        this.description = description;
    }

    public Recipe_Step(String subtitle, String description) {
        this.subtitle = subtitle;
        this.description = description;
    }
    public Recipe_Step(String description, byte[] image) {
        this.description = description;
        this.image = image;
    }

    public Recipe_Step(String subtitle, String description, byte[] image) {
        this.subtitle = subtitle;
        this.description = description;
        this.image = image;
    }
}
