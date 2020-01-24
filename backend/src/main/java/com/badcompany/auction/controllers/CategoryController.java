package com.badcompany.auction.controllers;

import com.badcompany.auction.entities.Category;
import com.badcompany.auction.repositories.CategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository repository;

    @GetMapping
    public @ResponseBody ResponseEntity<?> categoryHandler(@RequestParam(name = "id")Optional<Long> id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("SubCategoryFilter", SimpleBeanPropertyFilter.serializeAllExcept("mainCategory"));
        mapper.setFilterProvider(filterProvider);
        List<Category> cat;
        if (id.isPresent()) {
            cat = new ArrayList<>();
            cat.add(repository.getOne(id.get()));
        }
        else {
            cat = repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        }
        return ResponseEntity.ok(mapper.writeValueAsString(cat));
    }
}
