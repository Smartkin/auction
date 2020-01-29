package com.badcompany.auction.repositories;

import com.badcompany.auction.entities.Category;
import com.badcompany.auction.entities.Lot;
import com.badcompany.auction.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
    Page<Lot> findAllByCategoriesInOrderById(List<Category> categories, Pageable pageable);
    Page<Lot> findAllByOwnerOrderById(User owner, Pageable pageable);
    Page<Lot> findAllByBidderOrderById(User bidder, Pageable pageable);
    Page<Lot> findAllByCategoriesInAndOwnerOrderById(List<Category> categories, User owner, Pageable pageable);
    Page<Lot> findAllByCategoriesInAndBidderOrderById(List<Category> categories, User bidder, Pageable pageable);
}
