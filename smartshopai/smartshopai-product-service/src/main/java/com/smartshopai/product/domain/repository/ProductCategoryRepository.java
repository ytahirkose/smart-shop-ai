package com.smartshopai.product.domain.repository;

import com.smartshopai.product.domain.entity.ProductCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends MongoRepository<ProductCategory, String> {
    
    Optional<ProductCategory> findBySlug(String slug);
    
    List<ProductCategory> findByParentCategoryId(String parentCategoryId);
    
    List<ProductCategory> findByLevel(Integer level);
    
    List<ProductCategory> findByActive(boolean active);
    
    List<ProductCategory> findByFeatured(boolean featured);
    
    List<ProductCategory> findByAiAnalysisCompleted(boolean completed);
    
    List<ProductCategory> findByNameContainingIgnoreCase(String name);
    
    boolean existsBySlug(String slug);
    
    boolean existsByName(String name);
}
