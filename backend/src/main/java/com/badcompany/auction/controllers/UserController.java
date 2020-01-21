package com.badcompany.auction.controllers;

import com.badcompany.auction.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/users")
    public String userHandler(@RequestParam(name = "id")Long id, @RequestParam(name = "simple")Optional<Boolean> simple) throws JSONException, IOException {
        JSONObject answer = new JSONObject();
        if (!simple.isPresent()) {
            answer.put("user", new ObjectMapper().writeValueAsString(userRepository.getOne(id)));
        }
        else {
            answer.put("user", userRepository.getOne(id).getUsername());
        }
        return answer.toString();
    }
}
