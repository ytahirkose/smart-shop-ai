package com.smartshopai.product.domain.service;

import com.smartshopai.product.domain.entity.Product;
import com.smartshopai.product.infrastructure.scraper.ScraperStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDataCollectorService {

    private final List<ScraperStrategy> scraperStrategies;
    private final ProductService productService;

    public Product collectProductData(String url) {
        log.info("Attempting to collect product data from URL: {}", url);

        ScraperStrategy strategy = scraperStrategies.stream()
                .filter(ScraperStrategy::canHandle)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No scraper strategy found for URL: " + url));

        try {
            Product scrapedProduct = strategy.scrape(url);
            
            // Check if product already exists and save/update it
            return productService.createOrUpdateProduct(scrapedProduct);

        } catch (IOException e) {
            log.error("Error while scraping data from URL {}: {}", url, e.getMessage());
            throw new RuntimeException("Failed to scrape product data from " + url, e);
        }
    }
}
