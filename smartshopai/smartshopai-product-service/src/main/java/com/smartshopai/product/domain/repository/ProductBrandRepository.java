package com.smartshopai.product.domain.repository;

import com.smartshopai.product.domain.entity.ProductBrand;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductBrandRepository extends MongoRepository<ProductBrand, String> {
    
    Optional<ProductBrand> findBySlug(String slug);
    
    Optional<ProductBrand> findByName(String name);
    
    List<ProductBrand> findByActive(boolean active);
    
    List<ProductBrand> findByVerified(boolean verified);
    
    List<ProductBrand> findByFeatured(boolean featured);
    
    List<ProductBrand> findByAiAnalysisCompleted(boolean completed);
    
    List<ProductBrand> findByNameContainingIgnoreCase(String name);
    
    List<ProductBrand> findByCountry(String country);
    
    List<ProductBrand> findByQualityTier(String qualityTier);
    
    List<ProductBrand> findByPriceRange(String priceRange);
    
    boolean existsBySlug(String slug);
    
    boolean existsByName(String name);
}
