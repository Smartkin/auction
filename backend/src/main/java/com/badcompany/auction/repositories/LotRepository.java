package com.badcompany.auction.repositories;

import com.badcompany.auction.entities.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {
    Lot findFirstByIdIsNotNull();
    Lot getFirstByIdAfter(Long id);
    @Query(value = "SELECT PRICE FROM LOT WHERE ID = :id", nativeQuery = true)
    Long getFirstById(Long id);
}
