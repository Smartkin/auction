package com.badcompany.auction.repositories;

import com.badcompany.auction.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findFirstByName(String name);
}
