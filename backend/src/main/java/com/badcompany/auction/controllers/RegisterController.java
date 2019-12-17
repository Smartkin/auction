package com.badcompany.auction.controllers;

import com.badcompany.auction.entities.User;
import com.badcompany.auction.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RegisterController {

    @Autowired
    UserRepository repository;

    @PostMapping("/api/register")
    public String registerHandler(@RequestParam(name = "name")Optional<String> name, @RequestParam(name = "surname") Optional<String> surname,
                                    @RequestParam(name = "password")Optional<String> password, @RequestParam(name = "email")Optional<String> email) throws JSONException {
        JSONObject answer = new JSONObject();
        answer.put("success", true);
        repository.save(new User(name.get(), surname.get(), password.get(), email.get(), "USER"));
        return answer.toString();
    }
}
