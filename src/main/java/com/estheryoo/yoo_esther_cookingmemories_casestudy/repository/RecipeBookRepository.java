package com.estheryoo.yoo_esther_cookingmemories_casestudy.repository;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeBookRepository extends JpaRepository<Recipe_Book,Long> {
    Recipe_Book findByTitle(String title);
    List<Recipe_Book> findByUser_Id(Long userId);
}
