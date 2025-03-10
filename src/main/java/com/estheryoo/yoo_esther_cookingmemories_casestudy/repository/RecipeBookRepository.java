package com.estheryoo.yoo_esther_cookingmemories_casestudy.repository;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Recipe_Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/*
Queries to the database for recipe books
*/

@Repository
public interface RecipeBookRepository extends JpaRepository<Recipe_Book,Long> {
    Recipe_Book findByTitle(String title);

    Page <Recipe_Book> findByUserId(Long userId, Pageable pageable);

    @Query("SELECT b FROM Recipe_Book b JOIN b.pages p WHERE p.id = :pageId")
    Page<Recipe_Book> findByPageId(@Param("pageId") Long pageId,Pageable pageable);

    @Query("SELECT b FROM Recipe_Book b JOIN b.user u WHERE u.id = :userId")
    List<Recipe_Book> findByUser_Id(@Param("userId") Long userId);

    @Query("SELECT b FROM Recipe_Book b JOIN b.pages p WHERE p.id = :pageId")
    List<Recipe_Book> findByPage_Id(@Param("pageId") Long pageId);
}
