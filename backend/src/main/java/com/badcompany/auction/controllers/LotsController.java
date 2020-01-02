package com.badcompany.auction.controllers;


import com.badcompany.auction.entities.Lot;
import com.badcompany.auction.payload.request.BidRequest;
import com.badcompany.auction.payload.request.LotRequest;
import com.badcompany.auction.payload.response.MessageResponse;
import com.badcompany.auction.payload.response.PriceResponse;
import com.badcompany.auction.repositories.LotRepository;
import com.badcompany.auction.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lots")
public class LotsController {

    @Autowired
    LotRepository lotRepository;

    @GetMapping
    public String lotsHandler(@RequestParam(name = "type")String type, @RequestParam(name = "id")Optional<Long> id, @RequestParam(name = "count")Optional<Long> count) throws JSONException {
        JSONObject answer = new JSONObject();
        switch(type){
            case "single":
                if (id.isPresent()){
                    answer.accumulate("lots", lotRepository.getOne(id.get()));
                }
                break;
            case "multiple":
                Lot lotsFirst = lotRepository.findFirstByIdIsNotNull();
                if (id.isPresent()){
                    lotsFirst = lotRepository.getOne(id.get());
                }
                answer.accumulate("lots", lotsFirst);
                for(long i = 0L; i < count.get() - 1; ++i) {
                    lotsFirst = lotRepository.getFirstByIdAfter(lotsFirst.getId());
                    answer.accumulate("lots", lotsFirst);
                }
                break;
        }
        return answer.toString();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('EVALUATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createLot(@Valid @RequestBody LotRequest lotRequest) {
        // Get authorized user data
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Lot lot = new Lot(userDetails.getId(), lotRequest.getStartPrice(), lotRequest.getName(), lotRequest.getDescription(), lotRequest.getBuyout());

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
        bidLot.setBidderID(userDetails.getId());
        bidLot.setPrice(bidLot.getPrice() + bidRequest.getPriceIncrease());

        // Save changes
        lotRepository.save(bidLot);

        return ResponseEntity.ok(new MessageResponse("Ставка сделана!"));
    }

    @MessageMapping("/lotPrice/{lotId}")
    @SendTo("/lots/priceChange/{lotId}")
    public PriceResponse priceMessager(@DestinationVariable String lotId) {
        return new PriceResponse(String.valueOf(lotRepository.getFirstById(Long.parseLong(lotId))));
    }
}
