package com.estheryoo.yoo_esther_cookingmemories_casestudy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name="recipe_books")
public class Recipe_Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    //on create set a date automatically
    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="book_pages",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name = "page_id"))
    private List<Recipe_Page> pages;

   public Recipe_Book(String title) {
       this.title = title;
   }

}
