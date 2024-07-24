package com.estheryoo.yoo_esther_cookingmemories_casestudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/*
error endpoints
*/

@Controller
public class ErrorController {
    @GetMapping("/404")
    public String error404() {
        return "error/404";
    }
}
