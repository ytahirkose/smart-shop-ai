package com.smartshopai.product.domain.service;

import com.smartshopai.common.exception.BusinessException;
import com.smartshopai.common.exception.NotFoundException;
import com.smartshopai.product.domain.entity.ProductBrand;
import com.smartshopai.product.domain.repository.ProductBrandRepository;
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
public class ProductBrandService {

    private final ProductBrandRepository productBrandRepository;

    public ProductBrand createBrand(ProductBrand brand) {
        log.info("Creating new product brand: {}", brand.getName());
        
        // Validate brand data
        validateBrandData(brand);
        
        // Check if brand already exists
        if (productBrandRepository.existsBySlug(brand.getSlug())) {
            throw new BusinessException("Brand with slug " + brand.getSlug() + " already exists");
        }
        
        if (productBrandRepository.existsByName(brand.getName())) {
            throw new BusinessException("Brand with name " + brand.getName() + " already exists");
        }
        
        // Set default values
        brand.setActive(true);
        brand.setVerified(false);
        brand.setFeatured(false);
        brand.prePersist();
        
        ProductBrand savedBrand = productBrandRepository.save(brand);
        log.info("Product brand created successfully with ID: {}", savedBrand.getId());
        
        return savedBrand;
    }

    @Cacheable(value = "brands", key = "#id")
    public ProductBrand findById(String id) {
        log.debug("Finding product brand by ID: {}", id);
        return productBrandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProductBrand", id));
    }

    @Cacheable(value = "brands", key = "#slug")
    public ProductBrand findBySlug(String slug) {
        log.debug("Finding product brand by slug: {}", slug);
        return productBrandRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("ProductBrand with slug", slug));
    }

    public ProductBrand findByName(String name) {
        log.debug("Finding product brand by name: {}", name);
        return productBrandRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("ProductBrand with name", name));
    }

    public List<ProductBrand> findActiveBrands() {
        log.debug("Finding active product brands");
        return productBrandRepository.findByActive(true);
    }

    public List<ProductBrand> findVerifiedBrands() {
        log.debug("Finding verified product brands");
        return productBrandRepository.findByVerified(true);
    }

    public List<ProductBrand> findFeaturedBrands() {
        log.debug("Finding featured product brands");
        return productBrandRepository.findByFeatured(true);
    }

    public List<ProductBrand> findBrandsForAiAnalysis() {
        log.debug("Finding brands for AI analysis");
        return productBrandRepository.findByAiAnalysisCompleted(false);
    }

    public List<ProductBrand> searchBrands(String name) {
        log.debug("Searching product brands by name: {}", name);
        return productBrandRepository.findByNameContainingIgnoreCase(name);
    }

    public List<ProductBrand> findByCountry(String country) {
        log.debug("Finding product brands by country: {}", country);
        return productBrandRepository.findByCountry(country);
    }

    public List<ProductBrand> findByQualityTier(String qualityTier) {
        log.debug("Finding product brands by quality tier: {}", qualityTier);
        return productBrandRepository.findByQualityTier(qualityTier);
    }

    public List<ProductBrand> findByPriceRange(String priceRange) {
        log.debug("Finding product brands by price range: {}", priceRange);
        return productBrandRepository.findByPriceRange(priceRange);
    }

    @CacheEvict(value = "brands", key = "#brand.id")
    public ProductBrand updateBrand(ProductBrand brand) {
        log.info("Updating product brand with ID: {}", brand.getId());
        
        ProductBrand existingBrand = findById(brand.getId());
        
        // Update fields
        existingBrand.setName(brand.getName());
        existingBrand.setDescription(brand.getDescription());
        existingBrand.setSlug(brand.getSlug());
        existingBrand.setLogoUrl(brand.getLogoUrl());
        existingBrand.setWebsiteUrl(brand.getWebsiteUrl());
        existingBrand.setCountry(brand.getCountry());
        existingBrand.setFoundedYear(brand.getFoundedYear());
        existingBrand.setActive(brand.isActive());
        existingBrand.setVerified(brand.isVerified());
        existingBrand.setFeatured(brand.isFeatured());
        existingBrand.setTargetAudience(brand.getTargetAudience());
        existingBrand.setPriceRange(brand.getPriceRange());
        existingBrand.setQualityTier(brand.getQualityTier());
        existingBrand.setMarketPosition(brand.getMarketPosition());
        
        existingBrand.preUpdate();
        
        ProductBrand updatedBrand = productBrandRepository.save(existingBrand);
        log.info("Product brand updated successfully with ID: {}", updatedBrand.getId());
        
        return updatedBrand;
    }

    @CacheEvict(value = "brands", key = "#id")
    public void deleteBrand(String id) {
        log.info("Deleting product brand with ID: {}", id);
        
        ProductBrand brand = findById(id);
        productBrandRepository.delete(brand);
        log.info("Product brand deleted successfully with ID: {}", id);
    }

    public void updateAiInsights(String brandId, String aiInsights, String brandPersonality, 
                                String qualityScore, String valueForMoney, String customerSatisfaction) {
        log.info("Updating AI insights for brand: {}", brandId);
        
        ProductBrand brand = findById(brandId);
        brand.setAiInsights(aiInsights);
        brand.setBrandPersonality(brandPersonality);
        brand.setQualityScore(qualityScore);
        brand.setValueForMoney(valueForMoney);
        brand.setCustomerSatisfaction(customerSatisfaction);
        brand.setAiAnalysisCompleted(true);
        brand.preUpdate();
        
        productBrandRepository.save(brand);
        log.info("AI insights updated for brand: {}", brandId);
    }

    private void validateBrandData(ProductBrand brand) {
        if (brand.getName() == null || brand.getName().trim().isEmpty()) {
            throw new BusinessException("Brand name is required");
        }
        
        if (brand.getSlug() == null || brand.getSlug().trim().isEmpty()) {
            throw new BusinessException("Brand slug is required");
        }
        
        // Slug format validation
        if (!brand.getSlug().matches("^[a-z0-9-]+$")) {
            throw new BusinessException("Invalid slug format. Use only lowercase letters, numbers, and hyphens");
        }
    }
}
