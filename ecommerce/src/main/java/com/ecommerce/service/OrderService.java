package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Order;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.OrderRepository;


@Service
public class OrderService {

    @Autowired
private CartService cartService;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    // Order place karo
    public Order placeOrder(Order order) {
        Product product = productService.getProductById(order.getProductId());

        // Stock check karo
        if (product.getStock() < order.getQuantity()) {
            throw new RuntimeException("Not enough stock! Available: " + product.getStock());
        }

        // Total price set karo
        order.setTotalPrice(product.getPrice() * order.getQuantity());
        order.setStatus("PENDING");

        // Stock kam karo
        product.setStock(product.getStock() - order.getQuantity());
        productService.updateProduct(product.getId(), product);
        
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(order.getUserId()); // Order save hone ke baad cart clear
        return savedOrder;

    }

    // User ke saare orders
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // Sabhi orders (admin)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Order status update karo
    public Order updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
