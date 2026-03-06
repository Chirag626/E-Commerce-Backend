package com.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Product;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Naya product add karo
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Saare products dekho
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ID se product dekho
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // Category se products dhundho
    public List<Product> getByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // Keyword se search karo
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    // Price range filter
    public List<Product> getByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    // Price low to high sort
    public List<Product> getProductsSortedLowToHigh() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    // Price high to low sort
    public List<Product> getProductsSortedHighToLow() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    // Advanced filter - sab optional
    public List<Product> filterProducts(String category, String keyword,
                                        Double minPrice, Double maxPrice) {
        return productRepository.filterProducts(category, keyword, minPrice, maxPrice);
    }

    // Product update karo
    public Product updateProduct(Long id, Product updatedProduct) {

        // ID ke alawa baaki fields null ho sakti hain!
        Product existing = getProductById(id);         // pehle DB se fetch karo
        existing.setName(updatedProduct.getName());
        existing.setDescription(updatedProduct.getDescription());
        existing.setPrice(updatedProduct.getPrice());
        existing.setStock(updatedProduct.getStock());
        existing.setCategory(updatedProduct.getCategory());
        return productRepository.save(existing);       // updated product save karo :  sirf woh fields update karo jo aaye
    }

    // Product delete karo
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
