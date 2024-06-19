package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@ComponentScan
@Controller
public class ViewController {

    @GetMapping("/")
    public String home(){return "fragments/home";}

    @GetMapping("/default")
    public String defaultPage(){return "layout/defaultLayout";}
}
