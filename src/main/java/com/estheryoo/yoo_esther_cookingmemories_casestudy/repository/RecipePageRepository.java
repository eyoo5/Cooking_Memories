package com.estheryoo.yoo_esther_cookingmemories_casestudy.repository;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipePageRepository extends JpaRepository<Recipe_Page, Long> {
    Recipe_Page findByTitle(String title);
    Page<Recipe_Page> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM Recipe_Page p JOIN p.books b WHERE b.id = :bookId")
    Page<Recipe_Page>findByBookId(@Param("bookId")Long bookId, Pageable pageable);
}
