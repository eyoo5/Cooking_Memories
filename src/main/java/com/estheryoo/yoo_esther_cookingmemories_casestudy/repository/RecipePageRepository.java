package com.estheryoo.yoo_esther_cookingmemories_casestudy.repository;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipePageRepository extends JpaRepository<Recipe_Page, Long> {
    Recipe_Page findByTitle(String title);

}
