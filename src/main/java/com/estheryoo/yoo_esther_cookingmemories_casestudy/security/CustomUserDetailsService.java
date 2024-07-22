package com.estheryoo.yoo_esther_cookingmemories_casestudy.security;

import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.Role;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.entity.User;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.RoleRepository;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;


    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            // Fetch Role entity from RoleRepository
            Role userRole = roleRepository.findById(user.getRole().getId())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName())));

        } else {
            throw new UsernameNotFoundException("Invalid email or password.");
        }
    }

}
