package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;


import com.estheryoo.yoo_esther_cookingmemories_casestudy.security.CustomUserDetailsService;
import com.estheryoo.yoo_esther_cookingmemories_casestudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String defaultPage(){return "fragments/home";}

    @GetMapping("/home")
    public String home(){return "fragments/home";}

    @GetMapping("/login")
    public String login(){
        return "fragments/login";
    }

    // validating email redirects back to login with error
    @PostMapping("/login")
    public String loggingIn(@RequestParam String email, @RequestParam String password, Model model) {
            model.addAttribute("error","Incorrect email or password");
            return "/fragments/login";//return with error page with error message
    }



//    @PostMapping("/login")
//    public String authenticateUser(@RequestParam("email") String email,
//                                   @RequestParam("password") String password,
//                                   Model model) {
//        try{
//
//        // Perform Authentication
//        Authentication authentication = authenticationManager.authenticate(
//                new EmailPasswordAuthenticationToken(email, password));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        if(authentication.isAuthenticated()) {
//            UserDTO userDTO = userService.findByEmail(email);
//            Long id = userDTO.getId();
//
//            return "redirect:/user/"+id;
//        }else{
//            model.addAttribute("error","Incorrect email or password");
//            return "/fragments/login";//return with error page with error message
//        }
//        }catch(Exception e){
//            model.addAttribute("error","An error occured during Login");
//            return "/fragments/login";
//        }
//    }

}
