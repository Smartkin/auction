package com.badcompany.auction.repositories;

import com.badcompany.auction.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT USERNAME FROM USERS WHERE ID = :id", nativeQuery = true)
    String getUsernameById(Long id);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
