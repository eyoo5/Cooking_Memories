package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;


import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@ComponentScan
@Controller
public class LoginController {

    @GetMapping("/")
    public String defaultPage(){return "fragments/home";}

    @GetMapping("/home")
    public String home(){return "fragments/home";}

    @GetMapping("/login")
    public String login(){
        return "fragments/login";
    }

    // validating request from form => then it redirects to  /user endpoint.
    @PostMapping("/login")
    public String loginPost(@RequestParam String email, @RequestParam String password, Model model) {
        if(email.equals("eyoo@gmail.com") && password.equals("1234")) {
            return "redirect:/user{id}";
        }else{
            model.addAttribute("error","Incorrect email or password");
            return "/fragments/login";//return with error page with error message
        }
    }
}
