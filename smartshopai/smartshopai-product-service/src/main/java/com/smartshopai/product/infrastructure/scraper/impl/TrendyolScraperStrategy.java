package com.smartshopai.product.infrastructure.scraper.impl;

import com.smartshopai.product.domain.entity.Product;
import com.smartshopai.product.infrastructure.scraper.PlaywrightService;
import com.smartshopai.product.infrastructure.scraper.ScraperStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Scraping strategy for Trendyol e-commerce site
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TrendyolScraperStrategy implements ScraperStrategy {

    private final PlaywrightService playwrightService;

    @Override
    public boolean canHandle(String url) {
        return url != null && url.contains("trendyol.com");
    }

    @Override
    public Product scrape(String url) {
        return playwrightService.executeScrapingTask(page -> {
            try {
                log.info("Starting to scrape Trendyol URL: {}", url);
                page.navigate(url);

                // Wait for the main product container to ensure the page is loaded
                page.waitForSelector("div.product-container");

                // Extract product information
                String name = page.locator("h1.pr-new-br").innerText();
                String priceText = page.locator("div.product-price-container span.prc-dsc").innerText();
                String imageUrl = page.locator("div.base-product-image img").first().getAttribute("src");
                String description = page.locator("div.detail-desc-list").innerText();

                // Extract specifications
                Map<String, Object> specifications = new HashMap<>();
                page.locator("ul.detail-attr-container li.detail-attr-item").all().forEach(spec -> {
                    String key = spec.locator("span").first().innerText();
                    String value = spec.locator("span").last().innerText();
                    if (key != null && value != null) {
                        specifications.put(key.trim(), value.trim());
                    }
                });

                // Parse price
                BigDecimal price = parsePrice(priceText);

                log.info("Successfully scraped product: {}, Price: {}, Image: {}", name, price, imageUrl);

                return Product.builder()
                        .name(name)
                        .description(description)
                        .url(url)
                        .mainImage(imageUrl)
                        .price(price)
                        .specifications(specifications)
                        .currency("TRY")
                        .source("TRENDYOL")
                        .build();

            } catch (Exception e) {
                log.error("Failed to scrape Trendyol URL: {}", url, e);
                throw new RuntimeException("Scraping failed for URL: " + url, e);
            }
        });
    }

    private BigDecimal parsePrice(String priceText) {
        if (priceText == null || priceText.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        try {
            // Remove currency symbols and non-numeric characters, replace comma with dot
            String cleanPrice = priceText.replaceAll("[^\\d,]", "").replace(",", ".");
            return new BigDecimal(cleanPrice);
        } catch (NumberFormatException e) {
            log.warn("Failed to parse price: {}", priceText);
            return BigDecimal.ZERO;
        }
    }
}
