package com.smartshopai.product.application.service;

import com.smartshopai.product.application.dto.request.CreateProductRequest;
import com.smartshopai.product.application.dto.request.ProductAnalysisRequest;
import com.smartshopai.product.application.dto.request.ProductComparisonRequest;
import com.smartshopai.product.application.dto.request.ProductSearchRequest;
import com.smartshopai.product.application.dto.response.ProductAnalysisResponse;
import com.smartshopai.product.application.dto.response.ProductComparisonResponse;
import com.smartshopai.product.application.dto.response.ProductResponse;
import com.smartshopai.product.application.mapper.ProductMapper;
import com.smartshopai.product.domain.entity.Product;
import com.smartshopai.product.domain.service.ProductService;
import com.smartshopai.product.infrastructure.client.AnalysisServiceClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Application service for product operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductApplicationService {

    private final ProductService productService;
    private final ProductMapper productMapper;
    private final AnalysisServiceClient analysisServiceClient;
    // TODO: Add ChatModel when Spring AI is available
    // private final ChatModel chatModel;

    public ProductResponse createProduct(CreateProductRequest request) {
        log.info("Creating new product: {}", request.getName());
        
        Product product = productMapper.toEntity(request);
        Product savedProduct = productService.createProduct(product);
        
        return productMapper.toResponse(savedProduct);
    }

    public Optional<ProductResponse> getProductById(String productId) {
        log.debug("Getting product by ID: {}", productId);
        productService.incrementViewCount(productId); // Increment view count on each fetch
        Product product = productService.getProductById(productId);
        return Optional.ofNullable(productMapper.toResponse(product));
    }

    public List<ProductResponse> getAllProducts() {
        log.debug("Getting all products");
        
        List<Product> products = productService.getAllProducts();
        return productMapper.toResponseList(products);
    }

    public ProductResponse updateProduct(String productId, CreateProductRequest request) {
        log.info("Updating product: {}", productId);
        
        Product product = productMapper.toEntity(request);
        product.setId(productId);
        Product updatedProduct = productService.updateProduct(product);
        
        return productMapper.toResponse(updatedProduct);
    }

    public void deleteProduct(String productId) {
        log.info("Deleting product: {}", productId);
        productService.deleteProduct(productId);
    }

    public List<ProductResponse> searchProducts(ProductSearchRequest request) {
        log.debug("Searching products with query: {}", request.getQuery());
        
        List<Product> products = productService.searchProducts(
            request.getQuery(),
            request.getCategory(),
            request.getBrand(),
            request.getMinPrice() != null ? java.math.BigDecimal.valueOf(request.getMinPrice()) : null,
            request.getMaxPrice() != null ? java.math.BigDecimal.valueOf(request.getMaxPrice()) : null,
            request.getSortBy(),
            request.getSortOrder(),
            request.getPage(),
            request.getSize()
        );
        
        return productMapper.toResponseList(products);
    }

    public ProductAnalysisResponse analyzeProduct(ProductAnalysisRequest request) {
        log.info("Analyzing product: {} for user: {}", request.getProductId(), request.getUserId());
        
        // This would typically call an AI service
        return ProductAnalysisResponse.builder()
                .productId(request.getProductId())
                .technicalSummary("AI-powered product analysis")
                .comparisonSummary("Product analysis completed")
                .aiInsights("quality: high, value: good")
                .userRecommendation("Good product")
                .analysisCompleted(true)
                .build();
    }

    public ProductComparisonResponse compareProducts(ProductComparisonRequest request) {
        if (request.getProductIds() == null || request.getProductIds().size() < 2) {
            throw new IllegalArgumentException("At least two product IDs are required for comparison.");
        }
        String productId1 = request.getProductIds().get(0);
        String productId2 = request.getProductIds().get(1);
        log.info("Comparing products {} and {} for user: {}", productId1, productId2, request.getUserId());

        Product product1 = productService.getProductById(productId1);
        Product product2 = productService.getProductById(productId2);

        String promptString = """
            You are an expert product comparison analyst.
            Please provide a detailed comparison of the following two products.
            Analyze them based on their specifications, price, features, and overall value.
            Conclude with a clear recommendation on which product is a better choice for a specific type of user (e.g., \"for a budget-conscious student\", \"for a professional gamer\").

            Product A:
            Name: {name1}
            Description: {desc1}
            Price: {price1}
            Specifications: {specs1}

            Product B:
            Name: {name2}
            Description: {desc2}
            Price: {price2}
            Specifications: {specs2}
            """;

        // TODO: Implement AI prompt when Spring AI is available
        // PromptTemplate promptTemplate = new PromptTemplate(promptString);
        // Prompt prompt = promptTemplate.create(Map.of(...));

        // TODO: Implement AI comparison when Spring AI is available
        String comparisonResult = "AI comparison temporarily disabled - using default comparison logic";

        return ProductComparisonResponse.builder()
                .comparedProductId(productId2)
                .comparedProductName(product2.getName())
                .comparedProductBrand(product2.getBrand() != null ? product2.getBrand() : "N/A")
                .recommendation(comparisonResult)
                .isBetterAlternative(true) // Logic to determine this can be more complex
                .build();
    }

    public List<ProductResponse> getProductsByCategory(String category) {
        log.debug("Getting products by category: {}", category);
        
        List<Product> products = productService.getProductsByCategory(category);
        return productMapper.toResponseList(products);
    }

    public List<ProductResponse> getProductsByBrand(String brand) {
        log.debug("Getting products by brand: {}", brand);
        
        List<Product> products = productService.getProductsByBrand(brand);
        return productMapper.toResponseList(products);
    }

    public List<ProductResponse> getTrendingProducts() {
        log.debug("Getting trending products");
        
        List<Product> products = productService.getTrendingProducts();
        return productMapper.toResponseList(products);
    }

    public List<ProductResponse> getFeaturedProducts() {
        log.debug("Getting featured products");
        
        List<Product> products = productService.getFeaturedProducts();
        return productMapper.toResponseList(products);
    }

    public void setFeaturedStatus(String productId, boolean isFeatured) {
        log.info("Setting featured status for product {}: {}", productId, isFeatured);
        productService.setFeaturedStatus(productId, isFeatured);
    }

    public List<ProductResponse> getSimilarProducts(String productId) {
        log.debug("Getting similar products for: {}", productId);
        Product product = productService.getProductById(productId);

        if (product.getAnalytics() == null) {
            log.warn("Product {} has no analytics to find similar products.", productId);
            return List.of();
        }

        // TODO: Implement embeddings logic when analysis service is ready
        List<Double> embeddings = List.of();
        List<String> similarProductIds = analysisServiceClient.findSimilarProductIds(embeddings, 5); // Find top 5

        // Remove the original product's ID from the similar list if it exists
        similarProductIds.remove(productId);

        if (similarProductIds.isEmpty()) {
            return List.of();
        }

        return this.getProductsByIds(similarProductIds);
    }

    public List<ProductResponse> getProductAlternatives(String productId) {
        log.debug("Getting smart alternatives for product: {}", productId);

        // Step 1: Get the base product
        Product baseProduct = productService.getProductById(productId);
        
        // Step 2: Find a pool of candidates (similar products)
        List<Product> candidateProducts = productService.getSimilarProducts(productId);
        if (candidateProducts.isEmpty()) {
            return List.of();
        }

        // Step 3: Use AI to analyze and select the best alternatives
        String promptString = """
            You are an expert product analyst specializing in identifying value.
            A user is currently viewing the following product:
            ---
            BASE PRODUCT:
            - ID: {baseId}
            - Name: {baseName}
            - Price: {basePrice}
            - Description: {baseDesc}
            - Specifications: {baseSpecs}
            ---
            Here is a list of similar products. Your task is to select up to 2 products from this list that represent a clear "smart upgrade" or a better value proposition.
            A smart upgrade is a product that is slightly better in key features (e.g., performance, newer model, better brand) but is priced very similarly (e.g., no more than 20% more expensive).
            A better value is a product that offers almost the same quality for a lower price.

            CANDIDATE PRODUCTS:
            {candidates}
            ---
            Analyze the candidates against the base product. Return ONLY the product IDs of the best 1-2 alternatives, separated by commas. Do not return the base product ID. Do not add any extra text or explanation.
            Example response: product_id_1,product_id_2
            """;

        String candidatesAsString = candidateProducts.stream()
            .map(p -> String.format("- ID: %s, Name: %s, Price: %s, Specs: %s", 
                                    p.getId(), p.getName(), p.getPrice(), 
                                    p.getSpecifications() != null ? p.getSpecifications().toString() : "N/A"))
            .collect(Collectors.joining("\n"));

                // TODO: Implement AI prompt when Spring AI is available
        // PromptTemplate promptTemplate = new PromptTemplate(promptString);
        // Prompt prompt = promptTemplate.create(Map.of(...));

        // TODO: Implement AI response when Spring AI is available
        String aiResponse = "AI analysis temporarily disabled - using default recommendation logic";
        log.info("AI suggested alternatives: {}", aiResponse);

        List<String> alternativeIds = List.of(aiResponse.split(",\\s*"));
        if (alternativeIds.isEmpty() || (alternativeIds.size() == 1 && alternativeIds.get(0).isBlank())) {
            return List.of();
        }

        return productService.getProductsByIds(alternativeIds).stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public List<ProductResponse> compareProducts(List<String> productIds) {
        log.debug("Comparing products: {}", productIds);
        
        List<Product> products = productService.compareProducts(productIds);
        return productMapper.toResponseList(products);
    }

    public List<ProductResponse> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        log.debug("Getting products by price range: {} - {}", minPrice, maxPrice);
        
        List<Product> products = productService.getProductsByPriceRange(
            minPrice != null ? java.math.BigDecimal.valueOf(minPrice) : null,
            maxPrice != null ? java.math.BigDecimal.valueOf(maxPrice) : null
        );
        return productMapper.toResponseList(products);
    }

    public List<ProductResponse> getProductsByMinRating(Double minRating) {
        log.debug("Getting products by minimum rating: {}", minRating);
        
        List<Product> products = productService.getProductsByMinRating(minRating);
        return productMapper.toResponseList(products);
    }

    public List<ProductResponse> getInStockProducts() {
        log.debug("Getting in-stock products");
        
        List<Product> products = productService.getInStockProducts();
        return productMapper.toResponseList(products);
    }

    public ProductAnalysisResponse analyzeProductWithAI(String productId) {
        log.info("Forwarding analysis request for product {} to AI service", productId);
        return analysisServiceClient.analyzeProduct(productId);
    }

    public List<ProductResponse> getProductsByIds(List<String> productIds) {
        List<Product> products = productService.getProductsByIds(productIds);
        return productMapper.toResponseList(products);
    }

    public void linkAnalysisToProduct(String productId, String analysisId) {
        log.debug("Application Service: Linking analysis {} to product {}", analysisId, productId);
        productService.linkAnalysisToProduct(productId, analysisId);
    }
}
