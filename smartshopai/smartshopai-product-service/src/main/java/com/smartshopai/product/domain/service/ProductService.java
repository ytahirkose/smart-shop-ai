package com.smartshopai.product.domain.service;

import com.smartshopai.product.domain.entity.PriceHistory;
import com.smartshopai.product.domain.entity.Product;
import com.smartshopai.product.domain.entity.ProductAnalysis;
import com.smartshopai.product.domain.repository.PriceHistoryRepository;
import com.smartshopai.product.domain.repository.ProductRepository;
import com.smartshopai.product.domain.repository.ProductAnalysisRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final ProductAnalysisRepository productAnalysisRepository;

    @Transactional
    public Product createOrUpdateProduct(Product scrapedProduct) {
        Optional<Product> existingProductOpt = productRepository.findByProductUrl(scrapedProduct.getProductUrl());

        if (existingProductOpt.isPresent()) {
            // Product exists, update it
            Product existingProduct = existingProductOpt.get();
            log.info("Product found with URL: {}. Updating...", scrapedProduct.getProductUrl());

            existingProduct.setName(scrapedProduct.getName());
            existingProduct.setMainImageUrl(scrapedProduct.getMainImageUrl());
            existingProduct.setUpdatedAt(LocalDateTime.now());

            // Check if the price has changed
            if (scrapedProduct.getCurrentPrice() != null &&
                (existingProduct.getCurrentPrice() == null || scrapedProduct.getCurrentPrice().compareTo(existingProduct.getCurrentPrice()) != 0)) {

                log.info("Price changed for product {}. Old: {}, New: {}", existingProduct.getId(), existingProduct.getCurrentPrice(), scrapedProduct.getCurrentPrice());
                existingProduct.setCurrentPrice(scrapedProduct.getCurrentPrice());

                PriceHistory newPriceHistory = PriceHistory.builder()
                        .productId(existingProduct.getId())
                        .price(scrapedProduct.getCurrentPrice())
                        .currency(scrapedProduct.getCurrency())
                        .timestamp(LocalDateTime.now())
                        .vendor("Trendyol") // This should be dynamic based on strategy
                        .build();
                PriceHistory savedPriceHistory = priceHistoryRepository.save(newPriceHistory);

                if (existingProduct.getPriceHistories() == null) {
                    existingProduct.setPriceHistories(new ArrayList<>());
                }
                existingProduct.getPriceHistories().add(savedPriceHistory);
            }
            return productRepository.save(existingProduct);
        } else {
            // Product is new, create it
            log.info("No product found with URL: {}. Creating new product...", scrapedProduct.getProductUrl());
            LocalDateTime now = LocalDateTime.now();
            scrapedProduct.setCreatedAt(now);
            scrapedProduct.setUpdatedAt(now);
            scrapedProduct.setPriceHistories(new ArrayList<>());

            // Save product first to get an ID
            Product savedProduct = productRepository.save(scrapedProduct);

            PriceHistory initialPrice = PriceHistory.builder()
                    .productId(savedProduct.getId())
                    .price(savedProduct.getCurrentPrice())
                    .currency(savedProduct.getCurrency())
                    .timestamp(now)
                    .vendor("Trendyol") // This should be dynamic based on strategy
                    .build();
            PriceHistory savedInitialPrice = priceHistoryRepository.save(initialPrice);

            savedProduct.getPriceHistories().add(savedInitialPrice);
            
            // Save again to link the price history
            return productRepository.save(savedProduct);
        }
    }

    @Transactional
    public void linkAnalysisToProduct(String productId, String analysisId) {
        log.info("Linking analysis {} to product {}", analysisId, productId);
        Product product = getProductById(productId);
        
        // We assume analysisId is valid and exists in the ai-analysis-service DB.
        // We create a reference to it without fetching the full object.
        ProductAnalysis analysisRef = new ProductAnalysis();
        analysisRef.setId(analysisId);
        
        product.setAnalysis(analysisRef);
        product.setUpdatedAt(LocalDateTime.now());
        
        productRepository.save(product);
        log.info("Successfully linked analysis to product {}", productId);
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<Product> getProductsByIds(List<String> productIds) {
        log.info("Fetching {} products by IDs.", productIds.size());
        return productRepository.findAllById(productIds);
    }

    @Transactional
    public void incrementViewCount(String productId) {
        Product product = getProductById(productId);
        product.setViewCount(product.getViewCount() + 1);
        product.setLastViewedAt(LocalDateTime.now());
        productRepository.save(product);
    }

    public List<Product> getFeaturedProducts() {
        log.info("Fetching featured products.");
        return productRepository.findByFeaturedTrue();
    }

    public List<Product> getTrendingProducts() {
        log.info("Fetching trending products.");
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        return productRepository.findByLastViewedAtAfterOrderByViewCountDesc(lastWeek, PageRequest.of(0, 10));
    }

    @Transactional
    public void setFeaturedStatus(String productId, boolean isFeatured) {
        Product product = getProductById(productId);
        product.setFeatured(isFeatured);
        productRepository.save(product);
        log.info("Set featured status to {} for product {}", isFeatured, productId);
    }

    // Additional methods needed by ProductApplicationService
    
    @Transactional
    public Product createProduct(Product product) {
        log.info("Creating new product: {}", product.getName());
        LocalDateTime now = LocalDateTime.now();
        product.setCreatedAt(now);
        product.setUpdatedAt(now);
        product.setViewCount(0L);
        product.setFeatured(false);
        
        return productRepository.save(product);
    }
    
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }
    
    @Transactional
    public Product updateProduct(Product product) {
        log.info("Updating product: {}", product.getId());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    
    @Transactional
    public void deleteProduct(String productId) {
        log.info("Deleting product: {}", productId);
        Product product = getProductById(productId);
        productRepository.delete(product);
    }
    
    public List<Product> searchProducts(String query, String category, String brand, 
                                     BigDecimal minPrice, BigDecimal maxPrice, 
                                     String sortBy, String sortOrder, 
                                     Integer page, Integer size) {
        log.info("Searching products with query: {}, category: {}, brand: {}, price range: {} - {}", 
                query, category, brand, minPrice, maxPrice);
        
        // This is a simplified search implementation
        // In a real application, you would use Elasticsearch or similar search engine
        List<Product> allProducts = productRepository.findAll();
        
        return allProducts.stream()
                .filter(product -> query == null || product.getName().toLowerCase().contains(query.toLowerCase()))
                .filter(product -> category == null || (product.getCategory() != null && 
                        product.getCategory().getName().equalsIgnoreCase(category)))
                .filter(product -> brand == null || (product.getBrand() != null && 
                        product.getBrand().getName().equalsIgnoreCase(brand)))
                .filter(product -> minPrice == null || product.getCurrentPrice().compareTo(minPrice) >= 0)
                .filter(product -> maxPrice == null || product.getCurrentPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
    }
    
    // Additional methods needed by ProductApplicationService
    
    public List<Product> getProductsByCategory(String category) {
        log.info("Fetching products by category: {}", category);
        return productRepository.findByCategoryNameIgnoreCase(category);
    }
    
    public List<Product> getProductsByBrand(String brand) {
        log.info("Fetching products by brand: {}", brand);
        return productRepository.findByBrandNameIgnoreCase(brand);
    }
    
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Fetching products by price range: {} - {}", minPrice, maxPrice);
        if (minPrice != null && maxPrice != null) {
            return productRepository.findByCurrentPriceBetween(minPrice, maxPrice);
        } else if (minPrice != null) {
            return productRepository.findByCurrentPriceGreaterThanEqual(minPrice);
        } else if (maxPrice != null) {
            return productRepository.findByCurrentPriceLessThanEqual(maxPrice);
        }
        return productRepository.findAll();
    }
    
    public List<Product> getProductsByMinRating(Double minRating) {
        log.info("Fetching products with minimum rating: {}", minRating);
        return productRepository.findByAverageRatingGreaterThanEqual(minRating);
    }
    
    public List<Product> getInStockProducts() {
        log.info("Fetching in-stock products");
        // This is a simplified implementation - in a real app you'd have stock tracking
        return productRepository.findAll();
    }

    public List<Product> getSimilarProducts(String productId) {
        // Placeholder: return all products except the given one
        return productRepository.findAll().stream()
                .filter(p -> !p.getId().equals(productId))
                .collect(Collectors.toList());
    }

    public List<Product> compareProducts(List<String> productIds) {
        // Placeholder: return all products matching the given IDs
        return productRepository.findAllById(productIds);
    }
}
