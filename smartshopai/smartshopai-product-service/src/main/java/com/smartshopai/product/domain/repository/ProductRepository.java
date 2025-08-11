package com.smartshopai.product.domain.repository;

import com.smartshopai.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    /**
     * Finds a product by its unique EAN (European Article Number).
     * @param ean The EAN of the product.
     * @return An Optional containing the product if found.
     */
    Optional<Product> findByEan(String ean);

    /**
     * Finds a product by its source URL.
     * @param productUrl The URL of the product page.
     * @return An Optional containing the product if found.
     */
    Optional<Product> findByUrl(String url);

    /**
     * Performs a full-text search on the name and description fields.
     * Requires a text index on these fields in the database.
     * @param searchText The text to search for.
     * @param pageable Pagination information.
     * @return A Page of products matching the search text.
     */
    @Query("{'$text': {'$search': ?0}}")
    Page<Product> findByTextSearch(String searchText, Pageable pageable);

    /**
     * Finds all products marked as featured.
     * @return A list of featured products.
     */
    List<Product> findByFeaturedTrue();

    /**
     * Finds the top N products viewed since a certain date, ordered by view count descending.
     * @param date The date to look after.
     * @param pageable Should be created with PageRequest.of(0, N) to get top N.
     * @return A list of trending products.
     */
    List<Product> findByLastViewedAtAfterOrderByViewCountDesc(LocalDateTime date, Pageable pageable);
    
    /**
     * Finds products by category name (case-insensitive).
     * @param categoryName The name of the category.
     * @return A list of products in the specified category.
     */
    @Query("{'category.name': {$regex: ?0, $options: 'i'}}")
    List<Product> findByCategoryNameIgnoreCase(String categoryName);
    
    /**
     * Finds products by brand name (case-insensitive).
     * @param brandName The name of the brand.
     * @return A list of products from the specified brand.
     */
    @Query("{'brand.name': {$regex: ?0, $options: 'i'}}")
    List<Product> findByBrandNameIgnoreCase(String brandName);
    
    /**
     * Finds products within a price range.
     * @param minPrice The minimum price.
     * @param maxPrice The maximum price.
     * @return A list of products within the price range.
     */
    List<Product> findByCurrentPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Finds products with price greater than or equal to the specified price.
     * @param minPrice The minimum price.
     * @return A list of products with price >= minPrice.
     */
    List<Product> findByCurrentPriceGreaterThanEqual(BigDecimal minPrice);
    
    /**
     * Finds products with price less than or equal to the specified price.
     * @param maxPrice The maximum price.
     * @return A list of products with price <= maxPrice.
     */
    List<Product> findByCurrentPriceLessThanEqual(BigDecimal maxPrice);
    
    /**
     * Finds products with average rating greater than or equal to the specified rating.
     * @param minRating The minimum rating.
     * @return A list of products with rating >= minRating.
     */
    List<Product> findByAverageRatingGreaterThanEqual(Double minRating);
}
