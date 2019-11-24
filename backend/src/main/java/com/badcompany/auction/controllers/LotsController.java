package com.badcompany.auction.controllers;


import com.badcompany.auction.repositories.LotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LotsController {

    @Autowired
    LotRepository lotRepository;

    // TODO: Add optional filter parameters
    @GetMapping("/api/lots")
    public String lotsHandler(@RequestParam(name = "id")Optional<Long> id) throws JSONException {
        JSONObject answer = new JSONObject();
        if (!id.isPresent()) {
            answer.accumulate("lots", lotRepository.findAll());
        }
        else{
            answer.accumulate("lots", lotRepository.getOne(id.get()));
        }
        return answer.toString();
    }
}
