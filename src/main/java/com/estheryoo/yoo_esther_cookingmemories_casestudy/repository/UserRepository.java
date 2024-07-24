package com.estheryoo.yoo_esther_cookingmemories_casestudy.repository;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Queries to the database for users
*/

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    void deleteUserByEmail(String email);
}
