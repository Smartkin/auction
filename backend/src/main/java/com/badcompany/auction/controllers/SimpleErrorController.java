package com.badcompany.auction.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SimpleErrorController implements ErrorController {

    private static final String PATH = "/error";

    @GetMapping(PATH)
    public String errorGet() {
        return "error";
    }

    @PostMapping(PATH)
    public ResponseEntity<?> errorPost() {
        return ResponseEntity.badRequest().body("Ошибка POST запроса!");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}