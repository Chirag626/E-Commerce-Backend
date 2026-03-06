package com.ecommerce.controller;

import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET /api/products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // GET /api/products/1
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // GET /api/products/search?keyword=phone
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    // GET /api/products/category/Electronics
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getByCategory(category));
    }

    // GET /api/products/filter/price?min=500&max=2000
    @GetMapping("/filter/price")
    public ResponseEntity<List<Product>> filterByPrice(@RequestParam Double min,
                                                        @RequestParam Double max) {
        return ResponseEntity.ok(productService.getByPriceRange(min, max));
    }

    // GET /api/products/sort?order=low   OR   ?order=high
    @GetMapping("/sort")
    public ResponseEntity<List<Product>> sortByPrice(@RequestParam String order) {
        if (order.equalsIgnoreCase("high")) {
            return ResponseEntity.ok(productService.getProductsSortedHighToLow());
        }
        return ResponseEntity.ok(productService.getProductsSortedLowToHigh());
    }

    // GET /api/products/filter?category=Electronics&keyword=phone&min=500&max=5000
    // Sab params optional hain - jo chahiye woh do baaki mat do
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> advancedFilter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max) {
        return ResponseEntity.ok(productService.filterProducts(category, keyword, min, max));
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    // PUT /api/products/1
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                  @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    // DELETE /api/products/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted");
    }
}
