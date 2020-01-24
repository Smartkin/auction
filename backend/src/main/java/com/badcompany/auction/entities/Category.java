package com.badcompany.auction.entities;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Category mainCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainCategory")
    @JsonFilter("SubCategoryFilter")
    private Set<Category> subCategory = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Category mainCategory) {
        this.mainCategory = mainCategory;
    }

    public Set<Category> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Set<Category> subCategory) {
        this.subCategory = subCategory;
    }

    public Category() {
        super();
    }

    public Category(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
        this.mainCategory = null;
    }

    public Category(Long id, String name, Category mainCategory) {
        super();
        this.id = id;
        this.name = name;
        this.mainCategory = mainCategory;
    }

    public Category(String name) {
        super();
        this.name = name;
        this.mainCategory = null;
    }

    public Category(String name, Category mainCategory) {
        super();
        this.name = name;
        this.mainCategory = mainCategory;
    }
}
