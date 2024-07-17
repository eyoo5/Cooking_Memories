package com.estheryoo.yoo_esther_cookingmemories_casestudy.repository;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeStepRepository extends JpaRepository<Recipe_Step, Long> {
    @Query("SELECT rs FROM Recipe_Step rs WHERE rs.recipePage.id = :pageId")
    List <Recipe_Step> findByPage_Id(@Param("pageId") Long pageId);
}
