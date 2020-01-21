package com.badcompany.auction.controllers;


import com.badcompany.auction.entities.Lot;
import com.badcompany.auction.entities.User;
import com.badcompany.auction.payload.request.BidRequest;
import com.badcompany.auction.payload.request.LotRequest;
import com.badcompany.auction.payload.response.MessageResponse;
import com.badcompany.auction.payload.response.LotResponse;
import com.badcompany.auction.repositories.LotRepository;
import com.badcompany.auction.repositories.UserRepository;
import com.badcompany.auction.security.services.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/lots")
public class LotsController {

    @Autowired
    LotRepository lotRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public @ResponseBody ResponseEntity<?> lotsHandler(@RequestParam(name = "type")String type, @RequestParam(name = "page")Optional<Long> pageNum, @RequestParam(name = "id")Optional<Long> id, @RequestParam(name = "count")Optional<Long> count) throws JSONException, JsonProcessingException {
        List<String> lotList = new ArrayList<>();
        switch(type){
            case "single":
                if (id.isPresent()){
                    lotList.add(new ObjectMapper().writeValueAsString(lotRepository.getOne(id.get())));
                }
                break;
            case "multiple":
                Lot lotsFirst = lotRepository.findFirstByIdIsNotNull();
                if (id.isPresent()){
                    lotsFirst = lotRepository.getOne(id.get());
                }
                lotList.add(new ObjectMapper().writeValueAsString(lotsFirst));
                Long lotAmt = 5L;
                if (count.isPresent())
                    lotAmt = count.get();
                for(long i = 0L; i < lotAmt - 1; ++i) {
                    lotsFirst = lotRepository.getFirstByIdAfter(lotsFirst.getId());
                    lotList.add(new ObjectMapper().writeValueAsString(lotsFirst));
                }
                break;
        }
        return ResponseEntity.ok(lotList.toArray());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('EVALUATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createLot(@Valid @RequestBody LotRequest lotRequest) {
        // Get authorized user data
        System.out.println(lotRequest.getBuyout());
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        Lot lot = new Lot(user, lotRequest.getStartPrice(), lotRequest.getName(), lotRequest.getDescription(), Boolean.parseBoolean(lotRequest.getBuyout().toString()));

        lotRepository.save(lot);

        return ResponseEntity.ok(new MessageResponse("Лот успешно добавлен!"));
    }

    @PostMapping("/bid")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EVALUATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> bidOnLot(@Valid @RequestBody BidRequest bidRequest) {
        // Get authorized user data
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get lot he is bidding on
        Lot bidLot = lotRepository.getOne(bidRequest.getLotId());
        User user = userRepository.getOne(userDetails.getId());
        bidLot.setBidder(user);
        bidLot.setPrice(bidLot.getPrice() + bidRequest.getPriceIncrease());
        Date today = new Date();
        if (bidLot.getEndDate().getTime() - today.getTime() <= 1000 * 360) {
            Date extendTime = new Date();
            extendTime.setTime(today.getTime() + 1000 * 360);
            bidLot.setEndDate(extendTime);
        }

        // Save changes
        lotRepository.save(bidLot);

        return ResponseEntity.ok(new MessageResponse("Ставка сделана!"));
    }

    @MessageMapping("/lotPrice/{lotId}")
    @SendTo("/lots/dataChange/{lotId}")
    @Transactional
    public LotResponse lotMessenger(@DestinationVariable String lotId) {
        LotResponse response = new LotResponse();
        Lot lot = lotRepository.findById(Long.parseLong(lotId)).get();
        System.out.println(lot);
        Hibernate.initialize(lot.getBidder());
        response.setBidder(lot.getBidder().getUsername());
        response.setPrice(String.valueOf(lot.getPrice()));
        response.setEndDate(lot.getEndDate());
        return response;
    }
}
