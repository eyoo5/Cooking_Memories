package com.estheryoo.yoo_esther_cookingmemories_casestudy.entity;

import jakarta.persistence.*;
import lombok.*;

/* The recipe steps table that has a M:1 relationship with recipe steps */


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

    @Column
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;


    @ManyToOne
    @JoinColumn(name= "recipe_page_id")
    private Recipe_Page recipePage;

    public Recipe_Step(String description) {
        this.description = description;
    }

    public Recipe_Step(String subtitle, String description) {
        this.subtitle = subtitle;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Recipe_Step{" +
                "id=" + id +
                ", subtitle='" + subtitle + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                ", recipePage=" + recipePage +
                '}';
    }

}
