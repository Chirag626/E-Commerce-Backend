package com.ecommerce.controller;

import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // POST /api/cart/add
    // Body: { "userId": 1, "productId": 2, "quantity": 3 }
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody Map<String, Long> body) {
        Long userId    = body.get("userId");
        Long productId = body.get("productId");
        Integer quantity = body.get("quantity").intValue();
        return ResponseEntity.ok(cartService.addToCart(userId, productId, quantity));
    }

    // GET /api/cart/1  → user 1 ka cart dekho
    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }

    // GET /api/cart/1/total  → total price
    @GetMapping("/{userId}/total")
    public ResponseEntity<Double> getCartTotal(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartTotal(userId));
    }

    // PUT /api/cart/item/5?quantity=2
    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<?> updateQuantity(@PathVariable Long cartItemId,
                                            @RequestParam Integer quantity) {
        Cart updated = cartService.updateQuantity(cartItemId, quantity);
        if (updated == null) {
            return ResponseEntity.ok("Item removed (quantity was 0)");
        }
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/cart/item/5  → ek item hatao
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<String> removeItem(@PathVariable Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return ResponseEntity.ok("Item removed from cart");
    }

    // DELETE /api/cart/clear/1  → poora cart saaf karo
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared!");
    }
}
