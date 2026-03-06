package com.ecommerce.repository;

import com.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Category se products dhundho
    List<Product> findByCategory(String category);

    // Name me keyword search karo
    List<Product> findByNameContaining(String keyword);

    // Price range filter
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    // Price low to high
    List<Product> findAllByOrderByPriceAsc();

    // Price high to low
    List<Product> findAllByOrderByPriceDesc();

    // Advanced filter - sab optional params ek saath
    // IS NULL check isliye hai taaki agar param na diya toh ignore ho jaaye
    @Query("SELECT p FROM Product p WHERE " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:keyword  IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> filterProducts(@Param("category") String category,
                                 @Param("keyword")  String keyword,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice);
}
