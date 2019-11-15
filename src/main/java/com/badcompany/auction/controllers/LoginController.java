package com.badcompany.auction.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/login")
    public String loginHandler(){
        return "This is a test login page";
    }
}
