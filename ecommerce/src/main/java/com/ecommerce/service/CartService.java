package com.ecommerce.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    // Cart me item add karo
    public Cart addToCart(Long userId, Long productId, Integer quantity) {

        // Product exist karta hai ya nahi
        Product product = productService.getProductById(productId);

        // Stock check karo
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock! Available: " + product.getStock());
        }

        // Agar same product already cart me hai toh quantity badhao
        Optional<Cart> existing = cartRepository.findByUserIdAndProductId(userId, productId);
        if (existing.isPresent()) {
            Cart cartItem = existing.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return cartRepository.save(cartItem);
        }

        // Naya cart item banao
        Cart cartItem = new Cart();
        cartItem.setUserId(userId);
        cartItem.setProductId(productId);
        cartItem.setQuantity(quantity);
        return cartRepository.save(cartItem);
    }

    // User ka poora cart dekho
    public List<Cart> getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // Cart se ek item remove karo
    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    // Quantity update karo
    public Cart updateQuantity(Long cartItemId, Integer newQuantity) {
        Cart cartItem = cartRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found: " + cartItemId));

        if (newQuantity <= 0) {
            cartRepository.deleteById(cartItemId);
            return null;
        }

        cartItem.setQuantity(newQuantity);
        return cartRepository.save(cartItem);
    }

    // Poora cart clear karo (order place hone ke baad)
    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }

    // Cart ka total price calculate karo
 public Double getCartTotal(Long userId) {
    List<Cart> cartItems = cartRepository.findByUserId(userId);
    
    // Step 1: Saare productIds ek saath nikalo
    List<Long> productIds = cartItems.stream()
            .map(Cart::getProductId)
            .collect(Collectors.toList());

    // Step 2: EK HI DB call mein saare products lo
    List<Product> products = productRepository.findAllById(productIds);

    // Step 3: Products ka map banao (id → product) for quick lookup
    Map<Long, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getId, p -> p));

    // Step 4: Total calculate karo
    double total = 0;
    for (Cart item : cartItems) {
        Product product = productMap.get(item.getProductId());
        total += product.getPrice() * item.getQuantity();
    }
    return total;
}
}
