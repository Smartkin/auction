package com.badcompany.auction.controllers;


import com.badcompany.auction.entities.*;
import com.badcompany.auction.payload.request.BidRequest;
import com.badcompany.auction.payload.request.LotRequest;
import com.badcompany.auction.payload.response.LotCreateResponse;
import com.badcompany.auction.payload.response.MessageResponse;
import com.badcompany.auction.payload.response.LotResponse;
import com.badcompany.auction.repositories.CategoryRepository;
import com.badcompany.auction.repositories.ImageRepository;
import com.badcompany.auction.repositories.LotRepository;
import com.badcompany.auction.repositories.UserRepository;
//import com.badcompany.auction.search.LotSearchRepository;
import com.badcompany.auction.security.services.UserDetailsImpl;
import com.badcompany.auction.services.FileService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    FileService fileService;

//    @Autowired
//    LotSearchRepository searchRepository;

    @GetMapping
    public @ResponseBody ResponseEntity<?> lotsHandler(@RequestParam(name = "type")String type,
                                                       @RequestParam(name = "multipleType")Optional<String> multipleType,
                                                       @RequestParam(name = "page")Optional<Long> pageNum,
                                                       @RequestParam(name = "id")Optional<Long> id,
                                                       @RequestParam(name = "amount")Optional<Long> amount,
                                                       @RequestParam(name = "category")Optional<String> categoryName,
                                                       @RequestParam(name = "owner")Optional<String> ownerName,
                                                       @RequestParam(name = "bidder")Optional<String> bidderName,
                                                       @RequestParam(name = "sort")Optional<String> sortBy,
                                                       @RequestParam(name = "sort_descended")Optional<Boolean> desc,
                                                       @RequestParam(name = "search")Optional<String> searchQuery)
            throws JSONException, JsonProcessingException {
        JSONObject json = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        // Setup a filter since we only need usernames
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("LotOwnerFilter", SimpleBeanPropertyFilter.filterOutAllExcept("username"));
        filterProvider.addFilter("LotBidderFilter", SimpleBeanPropertyFilter.filterOutAllExcept("username"));
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
                List<Lot> lots = new ArrayList<>();
                int lotAmt = 5;
                int page = 1;
                long pageAmt = 1L;
                Page<Lot> lotsPage = null;
                Sort sort = Sort.by(Sort.Direction.ASC, "id");
                // Check for sorting
                if (sortBy.isPresent()) {
                    sort = Sort.by(desc.isPresent() && desc.get() ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy.get());
                }
                // Check for non negative page
                if (pageNum.isPresent() && pageNum.get() > 0) {
                    page = pageNum.get().intValue() - 1;
                }
                // Get for non negative amount
                if (amount.isPresent() && amount.get() > 0)
                    lotAmt = amount.get().intValue();
                if (multipleType.isPresent()) {
                    switch(multipleType.get()) {
                        case "categories": {
                            if (!categoryName.isPresent()) break;
                            List<Category> categories = collectAllCategories(categoryName.get());
                            if (categories == null) break;
                            lotsPage = lotRepository.findAllByCategoriesInOrderById(categories, PageRequest.of(page, lotAmt, sort));
                            break;
                        }
                        case "owner":
                            if (!ownerName.isPresent()) break;
                            lotsPage = lotRepository.findAllByOwnerOrderById(userRepository.findByUsername(ownerName.get()).get(), PageRequest.of(page, lotAmt, sort));
                            break;
                        case "bidder":
                            if (!bidderName.isPresent()) break;
                            lotsPage = lotRepository.findAllByBidderOrderById(userRepository.findByUsername(bidderName.get()).get(), PageRequest.of(page, lotAmt, sort));
                            break;
                        case "search":
                            if (!searchQuery.isPresent()) break;
                            System.out.println("Search query: " + searchQuery.get());
//                            lotsPage = searchRepository.findAllByName(searchQuery.get(), PageRequest.of(page, lotAmt));
                            break;
                        case "categories&owner": {
                            if (!categoryName.isPresent() || !ownerName.isPresent()) break;
                            List<Category> categories = collectAllCategories(categoryName.get());
                            if (categories == null) break;
                            lotsPage = lotRepository.findAllByCategoriesInAndOwnerOrderById(categories, userRepository.findByUsername(ownerName.get()).get(), PageRequest.of(page, lotAmt, sort));
                            break;
                        }
                        case "categories&bidder": {
                            if (!categoryName.isPresent() || !bidderName.isPresent()) break;
                            List<Category> categories = collectAllCategories(categoryName.get());
                            if (categories == null) break;
                            lotsPage = lotRepository.findAllByCategoriesInAndBidderOrderById(categories, userRepository.findByUsername(bidderName.get()).get(), PageRequest.of(page, lotAmt, sort));
                            break;
                        }
                        case "categories&search": {
                            if (!categoryName.isPresent() || !searchQuery.isPresent()) break;
                            List<Category> categories = collectAllCategories(categoryName.get());
                            if (categories == null) break;
//                            lotsPage = searchRepository.findAllByCategoriesInAndName(categories, searchQuery.get(), PageRequest.of(page, lotAmt));
                        }
                        case "all":
                        default:
                            lotsPage = lotRepository.findAll(PageRequest.of(page, lotAmt, sort));
                            break;
                    }
                }
                else {
                    lotsPage = lotRepository.findAll(PageRequest.of(page, lotAmt, sort));
                }
                if (lotsPage != null) {
                    pageAmt = lotsPage.getTotalPages();
                    lots = lotsPage.toList();
                }
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

    // Category collection by name
    private List<Category> collectAllCategories(String name) {
        System.out.println("Category name: " + name);
        Category catFound = categoryRepository.findFirstByName(name);
        if (name.equals("Все")) {
            return categoryRepository.findAll();
        }
        // If nonexistent category was passed
        if (catFound == null) return null;
        return collectAllCategories(catFound);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER') or hasRole('EVALUATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> createLot(@Valid @RequestBody LotRequest lotRequest) {
        // Get authorized user data
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getOne(userDetails.getId());
        Lot lot = new Lot(user, lotRequest.getStartPrice(), lotRequest.getName(), lotRequest.getDescription(), Boolean.parseBoolean(lotRequest.getBuyout().toString()));
        lot.getEndDate().setTime(new Date().getTime() + lotRequest.getTimeAmount());

        lotRepository.save(lot);

        return ResponseEntity.ok(new LotCreateResponse("Лот успешно добавлен!", lot.getId()));
    }

    @PostMapping("/bid")
    @Transactional
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EVALUATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> bidOnLot(@Valid @RequestBody BidRequest bidRequest) {
        // Get authorized user data
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Get lot he is bidding on
        Lot bidLot = lotRepository.getOne(bidRequest.getLotId());
        User user = userRepository.getOne(userDetails.getId());
        if (user.getUsername().equals(bidLot.getOwner().getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Нельзя делать ставки на собственные лоты!"));
        }
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

    @PostMapping("/uploadImages")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EVALUATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> uploadLotImages(@RequestParam(name = "images")MultipartFile[] files, @RequestParam(name = "lotId")Long lotId) {
        Lot lot = lotRepository.getOne(lotId);
        Set<Image> images = new HashSet<>();
        Arrays.stream(files).forEach(file -> {
            try {
                System.out.println(file);
                fileService.uploadFile(file, "lots/" + lotId + "/");
                Image image = new Image("lots/" + lotId + "/" + file.getOriginalFilename(), lot);
                images.add(image);
                imageRepository.save(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lot.setLotImages(images);
        lotRepository.save(lot);

        return ResponseEntity.ok(new MessageResponse("Картинки лота успешно загружены!"));
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
