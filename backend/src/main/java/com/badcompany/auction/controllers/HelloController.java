package com.badcompany.auction.controllers;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public String helloHandler() throws JSONException {
        JSONObject answer = new JSONObject();
        answer.accumulate("message", "Hello World!");
        return answer.toString();
    }
}
