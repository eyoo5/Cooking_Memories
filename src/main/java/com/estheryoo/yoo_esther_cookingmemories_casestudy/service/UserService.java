package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;


import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import org.springframework.stereotype.Component;
import java.util.List;

/*
Methods that use user repository queries to save, get, and update a user.
User entities are changed into a DTO (Data Transfer Object).
 */

@Component
public interface UserService {
    void saveUser(UserDTO userDTO);
    UserDTO findByEmail(String email);
    UserDTO findById(Long id);
    List<UserDTO> findAllUsers();
}
