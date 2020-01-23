package com.badcompany.auction.controllers;

import com.badcompany.auction.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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
        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.setDefaultFilter(SimpleBeanPropertyFilter.serializeAll());
        filterProvider.addFilter("UserOwnLotFilter", SimpleBeanPropertyFilter.serializeAllExcept("owner"));
        filterProvider.addFilter("UserBidLotFilter", SimpleBeanPropertyFilter.serializeAllExcept("bidder"));
        filterProvider.addFilter("LotOwnerFilter", SimpleBeanPropertyFilter.serializeAllExcept("owningLots", "biddingLots"));
        filterProvider.addFilter("LotBidderFilter", SimpleBeanPropertyFilter.serializeAllExcept("owningLots", "biddingLots"));
        mapper.setFilterProvider(filterProvider);
        String resString;
        if (!simple.isPresent()) {
            resString = mapper.writeValueAsString(userRepository.getOne(id));
        }
        else {
            resString = "{\"user\": \"" + userRepository.getOne(id).getUsername() + "\"}";
        }
        return resString;
    }
}
