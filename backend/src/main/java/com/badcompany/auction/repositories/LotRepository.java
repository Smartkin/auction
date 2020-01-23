package com.badcompany.auction.repositories;

import com.badcompany.auction.entities.Category;
import com.badcompany.auction.entities.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
    Lot getFirstByIdAfter(Long id);
    Page<Lot> findAllByCategoriesInOrderById(List<Category> categories, Pageable pageable);
}
