package com.badcompany.auction.controllers;


import com.badcompany.auction.entities.User;
import com.badcompany.auction.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    UserRepository repository;

    @GetMapping("/api/login")
    public String loginController(HttpSession session, @RequestParam("email")Optional<String> email,
                                  @RequestParam("password") Optional<String> password) throws JSONException {
        JSONObject answer = new JSONObject();
        answer.put("success", false);
        if (!email.isPresent() || !password.isPresent()){
            answer.accumulate("error", "Email или пароль не были введены! Повторите попытку!");
            return answer.toString();
        }
        List<User> users = repository.findAll();
        for (User user : users){
            if (user.getEmail().equals(email.get()) && user.getPassword().equals(password.get())){
                answer.put("success", true)
                        .put("sessionID", session.getId())
                        .put("name", user.getName())
                        .put("surname", user.getSurname())
                        .put("userID", user.getId());
                break;
            }
        }
        if (!answer.getBoolean("success")){
            answer.accumulate("error", "Email или пароль не верны! Повторите попытку!");
        }
        return answer.toString();
    }
}
