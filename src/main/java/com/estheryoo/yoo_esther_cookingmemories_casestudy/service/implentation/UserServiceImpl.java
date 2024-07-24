package com.estheryoo.yoo_esther_cookingmemories_casestudy.service.implentation;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.dto.UserDTO;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Role;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.User;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RoleRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.UserRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/*
Methods connect the repository to find user information and
converts this into a user DTO (Data Transfer Object).
It performs CRUD operations for users.
*/

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository
                           ,PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        //Find Role Entity and add it to user
        Role roleEntity = roleRepository.findByName("ROLE_USER");
        user.setRole(roleEntity);

        userRepository.save(user);
    }

    @Override
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return convertEntityToDTO(user);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found."));
        return convertEntityToDTO(user);
    }

    @Override
    public List <UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDTO(user))
                .collect(Collectors.toList());

    }


    private UserDTO convertEntityToDTO(User user){
        UserDTO userDTO = new UserDTO();
        if(user != null) {
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setRole(user.getRole().getName());
            if(user.getImage() != null){
                Long id = user.getImage().getId();
            userDTO.setImageId(id);
            }
            return userDTO;
        }else{
            return null;
        }
    }
}
