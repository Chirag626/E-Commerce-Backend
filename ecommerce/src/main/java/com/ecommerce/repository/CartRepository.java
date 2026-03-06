package com.ecommerce.repository;

import com.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // User ke saare cart items
    List<Cart> findByUserId(Long userId);

    // Check karo same product already cart me hai ya nahi
    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);

    // Order place hone ke baad poora cart delete karo
    void deleteByUserId(Long userId);
}
