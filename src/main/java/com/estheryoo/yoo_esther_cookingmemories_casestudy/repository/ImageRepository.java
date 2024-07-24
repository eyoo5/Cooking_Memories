package com.estheryoo.yoo_esther_cookingmemories_casestudy.repository;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/*
Queries to the database for images
*/

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i  WHERE i.user = :userId ")
    Image findByUserId(@Param("userId") Long userId);

    @Query("SELECT i FROM Image i WHERE i.book = :bookId ")
    Image findByBookId(@Param("bookId") Long bookId);

    @Query("SELECT i FROM Image i WHERE i.page = :pageId ")
    Image findByPageId(@Param("pageId") Long pageId);

    @Query("SELECT i FROM Image i WHERE i.step = :stepId ")
    Image findByStepId(@Param("stepId") Long stepId);
}
