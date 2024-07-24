package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;


import com.estheryoo.yoo_esther_cookingmemories_casestudy.security.CustomUserDetailsService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/*
Log in and log out endpoints for user. Also holds the home page.
*/


@ComponentScan
@Controller
public class LoginController {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/landing")
    public String landing(){
            return "fragments/LandingPageSignedOut";
    }

    @GetMapping("/home")
    public String home(){
            return "fragments/LandingPageSignedIn";
    }

    @GetMapping("/login")
    public String login(){
        return "fragments/login";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

}
