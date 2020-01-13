package com.badcompany.auction.controllers;

import com.badcompany.auction.payload.response.MessageResponse;
import org.apache.catalina.connector.ResponseFacade;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<Object>(new MessageResponse("Нет прав! Возможно требуется авторизация!"), HttpStatus.FORBIDDEN);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}