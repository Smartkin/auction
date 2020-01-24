package com.badcompany.auction.controllers;


import com.badcompany.auction.entities.Category;
import com.badcompany.auction.entities.Lot;
import com.badcompany.auction.entities.User;
import com.badcompany.auction.payload.request.BidRequest;
import com.badcompany.auction.payload.request.LotRequest;
import com.badcompany.auction.payload.response.MessageResponse;
import com.badcompany.auction.payload.response.LotResponse;
import com.badcompany.auction.repositories.CategoryRepository;
import com.badcompany.auction.repositories.LotRepository;
import com.badcompany.auction.repositories.UserRepository;
import com.badcompany.auction.security.services.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping
    public @ResponseBody ResponseEntity<?> lotsHandler(@RequestParam(name = "type")String type,
                                                       @RequestParam(name = "page")Optional<Long> pageNum,
                                                       @RequestParam(name = "id")Optional<Long> id,
                                                       @RequestParam(name = "amount")Optional<Long> amount,
                                                       @RequestParam(name = "category")Optional<String> categoryName)
            throws JSONException, JsonProcessingException {
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        // Setup a filter since we only need usernames
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("LotOwnerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("username"));
        filterProvider.addFilter("LotBidderFilter", SimpleBeanPropertyFilter.filterOutAllExcept("username"));
        filterProvider.addFilter("SubCategoryFilter", SimpleBeanPropertyFilter.serializeAllExcept("mainCategory"));
        mapper.setFilterProvider(filterProvider);
        // Fetch lots from the database
        String lotList = "[]";
        switch(type){
            case "single":
                if (id.isPresent()){
                    Lot lot = lotRepository.getOne(id.get());
                    lotList = mapper.writeValueAsString(lot);
                }
                break;
            case "multiple":
                List<Lot> lots;
                long lotAmt = 5L;
                long page = 1L;
                long pageAmt;
                Page<Lot> lotsPage;
                // Check for non negative page
                if (pageNum.isPresent() && pageNum.get() > 0) {
                    page = pageNum.get();
                }
                // Get for non negative amount
                if (amount.isPresent() && amount.get() > 0)
                    lotAmt = amount.get();
                // Get lots depending if category was requested
                if (categoryName.isPresent()) {
                    System.out.println("Category name: " + categoryName.get());
                    List<Category> categories = collectAllCategories(categoryRepository.findFirstByName(categoryName.get()));
                    categories.sort((c1, c2) -> (int)(c2.getId() - c1.getId()));
                    System.out.println(Arrays.toString(categories.toArray()));
                    lotsPage = lotRepository.findAllByCategoriesInOrderById(categories, PageRequest.of((int)page - 1, (int)lotAmt, Sort.by(Sort.Direction.ASC, "id")));
                }
                else {
                    lotsPage = lotRepository.findAll(PageRequest.of((int)page - 1, (int)lotAmt, Sort.by(Sort.Direction.ASC, "id")));
                }
                pageAmt = lotsPage.getTotalPages();
                lots = lotsPage.toList();
                lotList = mapper.writeValueAsString(lots);
                json.put("pages", pageAmt);
                break;
        }
        // Send the result
        json.put("lots", lotList);
        return ResponseEntity.ok(json.toString());
    }

    // Collects all requested categories and subcategories into a single set
    private List<Category> collectAllCategories(Category category) {
        List<Category> result = new ArrayList<>();
        result.add(category);
        for(Category cat : category.getSubCategory()) {
            result.addAll(collectAllCategories(cat));
        }
        return result;
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
