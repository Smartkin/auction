package com.badcompany.auction.controllers;

import com.badcompany.auction.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/users")
    public String userHandler(@RequestParam(name = "id")Long id, @RequestParam(name = "simple")Optional<Boolean> simple) throws JSONException {
        JSONObject answer = new JSONObject();
        if (!simple.isPresent()) {
            answer.put("user", userRepository.getOne(id));
        }
        else {
            answer.put("user", userRepository.getOne(id).getUsername());
        }
        return answer.toString();
    }
}
