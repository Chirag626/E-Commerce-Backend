package com.ecommerce.repository;

import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Email se user dhundho..
    Optional<User> findByEmail(String email);

    // Check karo email already exist karta hai ya nahi
    boolean existsByEmail(String email);
}
