package com.badcompany.auction.controllers;

import com.badcompany.auction.entities.EImageType;
import com.badcompany.auction.entities.Image;
import com.badcompany.auction.entities.User;
import com.badcompany.auction.payload.response.MessageResponse;
import com.badcompany.auction.repositories.ImageRepository;
import com.badcompany.auction.repositories.UserRepository;
import com.badcompany.auction.security.services.UserDetailsImpl;
import com.badcompany.auction.services.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    FileService fileService;

    @GetMapping
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

    @PostMapping("/changeAvatar")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EVALUATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> avatarHandler(@RequestParam(name = "avatar") MultipartFile file) throws IOException {
        // Get authorized user data
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        fileService.uploadFile(file, userDetails.getUsername() + "/");
        Image image = new Image(userDetails.getUsername() + "/" + file.getOriginalFilename(), EImageType.TYPE_USER);

        imageRepository.save(image);

        User user = userRepository.getOne(userDetails.getId());
        user.setAvatar(image);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Аватар успешно изменен!"));
    }
}
