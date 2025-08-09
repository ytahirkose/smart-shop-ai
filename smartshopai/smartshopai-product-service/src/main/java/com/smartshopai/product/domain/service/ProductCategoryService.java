package com.smartshopai.product.domain.service;

import com.smartshopai.common.exception.BusinessException;
import com.smartshopai.common.exception.NotFoundException;
import com.smartshopai.product.domain.entity.ProductCategory;
import com.smartshopai.product.domain.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategory createCategory(ProductCategory category) {
        log.info("Creating new product category: {}", category.getName());
        
        // Validate category data
        validateCategoryData(category);
        
        // Check if category already exists
        if (productCategoryRepository.existsBySlug(category.getSlug())) {
            throw new BusinessException("Category with slug " + category.getSlug() + " already exists");
        }
        
        if (productCategoryRepository.existsByName(category.getName())) {
            throw new BusinessException("Category with name " + category.getName() + " already exists");
        }
        
        // Set default values
        category.setActive(true);
        category.setFeatured(false);
        category.prePersist();
        
        ProductCategory savedCategory = productCategoryRepository.save(category);
        log.info("Product category created successfully with ID: {}", savedCategory.getId());
        
        return savedCategory;
    }

    @Cacheable(value = "categories", key = "#id")
    public ProductCategory findById(String id) {
        log.debug("Finding product category by ID: {}", id);
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProductCategory", id));
    }

    @Cacheable(value = "categories", key = "#slug")
    public ProductCategory findBySlug(String slug) {
        log.debug("Finding product category by slug: {}", slug);
        return productCategoryRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("ProductCategory with slug", slug));
    }

    public List<ProductCategory> findByParentCategoryId(String parentCategoryId) {
        log.debug("Finding product categories by parent ID: {}", parentCategoryId);
        return productCategoryRepository.findByParentCategoryId(parentCategoryId);
    }

    public List<ProductCategory> findByLevel(Integer level) {
        log.debug("Finding product categories by level: {}", level);
        return productCategoryRepository.findByLevel(level);
    }

    public List<ProductCategory> findActiveCategories() {
        log.debug("Finding active product categories");
        return productCategoryRepository.findByActive(true);
    }

    public List<ProductCategory> findFeaturedCategories() {
        log.debug("Finding featured product categories");
        return productCategoryRepository.findByFeatured(true);
    }

    public List<ProductCategory> findCategoriesForAiAnalysis() {
        log.debug("Finding categories for AI analysis");
        return productCategoryRepository.findByAiAnalysisCompleted(false);
    }

    public List<ProductCategory> searchCategories(String name) {
        log.debug("Searching product categories by name: {}", name);
        return productCategoryRepository.findByNameContainingIgnoreCase(name);
    }

    @CacheEvict(value = "categories", key = "#category.id")
    public ProductCategory updateCategory(ProductCategory category) {
        log.info("Updating product category with ID: {}", category.getId());
        
        ProductCategory existingCategory = findById(category.getId());
        
        // Update fields
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setSlug(category.getSlug());
        existingCategory.setParentCategoryId(category.getParentCategoryId());
        existingCategory.setLevel(category.getLevel());
        existingCategory.setImageUrl(category.getImageUrl());
        existingCategory.setIconUrl(category.getIconUrl());
        existingCategory.setMetaTitle(category.getMetaTitle());
        existingCategory.setMetaDescription(category.getMetaDescription());
        existingCategory.setKeywords(category.getKeywords());
        existingCategory.setActive(category.isActive());
        existingCategory.setFeatured(category.isFeatured());
        
        existingCategory.preUpdate();
        
        ProductCategory updatedCategory = productCategoryRepository.save(existingCategory);
        log.info("Product category updated successfully with ID: {}", updatedCategory.getId());
        
        return updatedCategory;
    }

    @CacheEvict(value = "categories", key = "#id")
    public void deleteCategory(String id) {
        log.info("Deleting product category with ID: {}", id);
        
        ProductCategory category = findById(id);
        
        // Check if category has subcategories
        List<ProductCategory> subcategories = findByParentCategoryId(id);
        if (!subcategories.isEmpty()) {
            throw new BusinessException("Cannot delete category with subcategories");
        }
        
        productCategoryRepository.delete(category);
        log.info("Product category deleted successfully with ID: {}", id);
    }

    public void updateAiInsights(String categoryId, String aiInsights, String trendingProducts, 
                                String categoryTrends) {
        log.info("Updating AI insights for category: {}", categoryId);
        
        ProductCategory category = findById(categoryId);
        category.setAiInsights(aiInsights);
        category.setTrendingProducts(trendingProducts);
        category.setCategoryTrends(categoryTrends);
        category.setAiAnalysisCompleted(true);
        category.preUpdate();
        
        productCategoryRepository.save(category);
        log.info("AI insights updated for category: {}", categoryId);
    }

    private void validateCategoryData(ProductCategory category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new BusinessException("Category name is required");
        }
        
        if (category.getSlug() == null || category.getSlug().trim().isEmpty()) {
            throw new BusinessException("Category slug is required");
        }
        
        // Slug format validation
        if (!category.getSlug().matches("^[a-z0-9-]+$")) {
            throw new BusinessException("Invalid slug format. Use only lowercase letters, numbers, and hyphens");
        }
    }
}
