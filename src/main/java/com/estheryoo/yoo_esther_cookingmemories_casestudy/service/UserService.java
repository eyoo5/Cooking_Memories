package com.estheryoo.yoo_esther_cookingmemories_casestudy.service;


import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    void saveUser(UserDTO userDTO);
    User findByEmail(String email);
    List<UserDTO> findAllUsers();
}
