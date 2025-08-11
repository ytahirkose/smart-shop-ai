package com.smartshopai.product.presentation.controller;

import com.smartshopai.common.dto.BaseResponse;
import com.smartshopai.product.application.dto.request.CreateProductRequest;
import com.smartshopai.product.application.dto.response.ProductAnalysisResponse;
import com.smartshopai.product.application.dto.response.ProductResponse;
import com.smartshopai.product.application.service.ProductApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for product management
 * Provides endpoints for product CRUD operations and AI analysis
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Product management operations")
public class ProductController {

    private final ProductApplicationService productApplicationService;

    @PostMapping
    
    @Operation(summary = "Create new product", description = "Creates a new product with AI analysis capabilities")
    public ResponseEntity<BaseResponse<ProductResponse>> createProduct(@Valid @RequestBody CreateProductRequest request) {
        log.info("Creating new product: {}", request.getName());
        ProductResponse productResponse = productApplicationService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(productResponse, "Product created successfully"));
    }

    @GetMapping("/{id}")
    
    @Operation(summary = "Get product by ID", description = "Retrieves product information by ID")
    public ResponseEntity<BaseResponse<ProductResponse>> getProductById(@PathVariable String id) {
        log.debug("Getting product by ID: {}", id);
        var productOpt = productApplicationService.getProductById(id);
        return productOpt
                .map(p -> ResponseEntity.ok(BaseResponse.success(p)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.error("Product not found")));
    }

    @GetMapping("/by-product-id/{productId}")
    
    @Operation(summary = "Get product by product ID", description = "Retrieves product information by product ID")
    public ResponseEntity<BaseResponse<ProductResponse>> getProductByProductId(@PathVariable String productId) {
        log.debug("Getting product by product ID: {}", productId);
        var productOpt = productApplicationService.getProductById(productId);
        return productOpt
                .map(p -> ResponseEntity.ok(BaseResponse.success(p)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.error("Product not found")));
    }

    @PutMapping("/{id}")
    
    @Operation(summary = "Update product", description = "Updates product information")
    public ResponseEntity<BaseResponse<ProductResponse>> updateProduct(@PathVariable String id,
                                                                     @Valid @RequestBody CreateProductRequest request) {
        log.info("Updating product with ID: {}", id);
        ProductResponse productResponse = productApplicationService.updateProduct(id, request);
        return ResponseEntity.ok(BaseResponse.success(productResponse, "Product updated successfully"));
    }

    @DeleteMapping("/{id}")
    
    @Operation(summary = "Delete product", description = "Deletes a product (marks as discontinued)")
    public ResponseEntity<BaseResponse<Void>> deleteProduct(@PathVariable String id) {
        log.info("Deleting product with ID: {}", id);
        productApplicationService.deleteProduct(id);
        return ResponseEntity.ok(BaseResponse.success(null, "Product deleted successfully"));
    }

    @GetMapping("/category/{category}")
    
    @Operation(summary = "Get products by category", description = "Retrieves products by category with pagination")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getProductsByCategory(@PathVariable String category,
                                                                                   Pageable pageable) {
        log.debug("Getting products by category: {}", category);
        var products = productApplicationService.getProductsByCategory(category);
        return ResponseEntity.ok(BaseResponse.success(products));
    }

    @GetMapping("/brand/{brand}")
    
    @Operation(summary = "Get products by brand", description = "Retrieves products by brand with pagination")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getProductsByBrand(@PathVariable String brand,
                                                                                Pageable pageable) {
        log.debug("Getting products by brand: {}", brand);
        var products = productApplicationService.getProductsByBrand(brand);
        return ResponseEntity.ok(BaseResponse.success(products));
    }

    @GetMapping("/search")
    
    @Operation(summary = "Search products", description = "Searches products by text")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> searchProducts(@RequestParam String q) {
        log.debug("Searching products with query: {}", q);
        var products = productApplicationService.searchProducts(
                com.smartshopai.product.application.dto.request.ProductSearchRequest.builder().query(q).build());
        return ResponseEntity.ok(BaseResponse.success(products));
    }

    @GetMapping("/price-range")
    
    @Operation(summary = "Get products by price range", description = "Retrieves products within a price range")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        log.debug("Getting products by price range: {} - {}", minPrice, maxPrice);
        var products = productApplicationService.getProductsByPriceRange(
                minPrice.doubleValue(), maxPrice.doubleValue());
        return ResponseEntity.ok(BaseResponse.success(products));
    }

    @GetMapping("/rating/{minRating}")
    
    @Operation(summary = "Get products by minimum rating", description = "Retrieves products with minimum rating")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getProductsByMinRating(@PathVariable Double minRating) {
        log.debug("Getting products by minimum rating: {}", minRating);
        List<ProductResponse> products = productApplicationService.getProductsByMinRating(minRating);
        return ResponseEntity.ok(BaseResponse.success(products));
    }

    @GetMapping("/featured")
    @Operation(summary = "Get featured products", description = "Retrieves a list of products marked as featured.")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getFeaturedProducts() {
        log.debug("Getting featured products");
        List<ProductResponse> products = productApplicationService.getFeaturedProducts();
        return ResponseEntity.ok(BaseResponse.success(products));
    }

    @GetMapping("/trending")
    @Operation(summary = "Get trending products", description = "Retrieves a list of trending products based on recent views.")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getTrendingProducts() {
        log.debug("Getting trending products");
        List<ProductResponse> products = productApplicationService.getTrendingProducts();
        return ResponseEntity.ok(BaseResponse.success(products));
    }

    @PatchMapping("/{id}/feature")
    
    @Operation(summary = "Set product's featured status", description = "Marks or unmarks a product as featured.")
    public ResponseEntity<BaseResponse<Void>> setFeaturedStatus(@PathVariable String id, @RequestParam boolean isFeatured) {
        log.info("Setting featured status to {} for product {}", isFeatured, id);
        productApplicationService.setFeaturedStatus(id, isFeatured);
        return ResponseEntity.ok(BaseResponse.success(null, "Product featured status updated successfully."));
    }

    @GetMapping("/in-stock")
    
    @Operation(summary = "Get in-stock products", description = "Retrieves products that are in stock")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getInStockProducts() {
        log.debug("Getting in-stock products");
        List<ProductResponse> products = productApplicationService.getInStockProducts();
        return ResponseEntity.ok(BaseResponse.success(products));
    }

    @PostMapping("/{productId}/analyze")
    
    @Operation(summary = "Analyze product with AI", description = "Performs AI analysis on a product")
    public ResponseEntity<BaseResponse<ProductAnalysisResponse>> analyzeProduct(@PathVariable String productId) {
        log.info("Analyzing product with ID: {}", productId);
        var analysis = productApplicationService.analyzeProductWithAI(productId);
        return ResponseEntity.ok(BaseResponse.success(analysis, "Product analysis completed"));
    }

    @GetMapping("/{productId}/alternatives")
    
    @Operation(summary = "Get product alternatives", description = "Retrieves alternative products")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getProductAlternatives(@PathVariable String productId) {
        log.debug("Getting alternatives for product: {}", productId);
        List<ProductResponse> alternatives = productApplicationService.getProductAlternatives(productId);
        return ResponseEntity.ok(BaseResponse.success(alternatives));
    }

    @GetMapping("/{productId}/similar")
    
    @Operation(summary = "Get similar products", description = "Retrieves similar products")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getSimilarProducts(@PathVariable String productId) {
        log.debug("Getting similar products for: {}", productId);
        List<ProductResponse> similar = productApplicationService.getSimilarProducts(productId);
        return ResponseEntity.ok(BaseResponse.success(similar));
    }

    @PostMapping("/compare")
    
    @Operation(summary = "Compare products", description = "Compares multiple products")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> compareProducts(@RequestBody List<String> productIds) {
        log.debug("Comparing products: {}", productIds);
        List<ProductResponse> products = productApplicationService.compareProducts(productIds);
        return ResponseEntity.ok(BaseResponse.success(products));
    }

    @PutMapping("/{productId}/link-analysis")
    
    @Operation(summary = "Link analysis to product", description = "Links an AI analysis to a product")
    public ResponseEntity<BaseResponse<Void>> linkAnalysisToProduct(@PathVariable String productId,
                                                                   @RequestBody String analysisId) {
        log.info("Linking analysis with ID {} to product with ID {}", analysisId, productId);
        productApplicationService.linkAnalysisToProduct(productId, analysisId);
        return ResponseEntity.ok(BaseResponse.success(null, "Analysis linked to product successfully"));
    }

    @PostMapping("/by-ids")
    public ResponseEntity<List<ProductResponse>> getProductsByIds(@RequestBody List<String> productIds) {
        List<ProductResponse> products = productApplicationService.getProductsByIds(productIds);
        return ResponseEntity.ok(products);
    }
}
