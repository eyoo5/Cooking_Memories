package com.estheryoo.yoo_esther_cookingmemories_casestudy.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table (name ="recipe_page")
public class Recipe_Page {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String videoLink;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
    /*
    @ElementCollection and @CollectionTable:
    Indicates that ingredients is
    a collection of simple type String
    that are stored in a separate table (ingredients)
     */
    @ElementCollection
    @CollectionTable(
            name = "ingredients",
            joinColumns = @JoinColumn(name = "recipe_id")
    )
    @Column(name = "ingredient")
    private List<String> ingredients = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "recipePage", cascade = CascadeType.ALL)
    private List<Recipe_Step> steps = new ArrayList<>();

    @ManyToMany (mappedBy = "pages", cascade = CascadeType.ALL)
    private List<Recipe_Book> books = new ArrayList<>();


    public Recipe_Page(String title){
        this.title = title;
    }

    public Recipe_Page(String title, String description, String videoLink) {
        this.title = title;
        this.description = description;
        this.videoLink = videoLink;
    }

    public Recipe_Page(String title, List<String> ingredients) {
        this.title = title;
        this.ingredients = ingredients;
    }

    public Recipe_Page(String title, List<String> ingredients, List<Recipe_Step> steps) {
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe_Page(String title, List<String> ingredients, String videoLink) {
        this.title = title;
        this.videoLink = videoLink;
        this.ingredients = ingredients;
    }

    public Recipe_Page(String title, String description, List<String> ingredients) {
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
    }

    // Constructor with all fields
    public Recipe_Page(String title, String description, String videoLink, List<String> ingredients) {
        this.title = title;
        this.description = description;
        this.videoLink = videoLink;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe_Page{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", videoLink='" + videoLink + '\'' +
                ", ingredients=" + ingredients +
                ", user=" + user +
                ", steps=" + steps +
                ", books=" + books +
                '}';
    }
}
