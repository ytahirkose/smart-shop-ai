package com.smartshopai.product.infrastructure.scraper;

import com.smartshopai.product.domain.entity.Product;

import java.io.IOException;

/**
 * Interface for web scraping strategies for different e-commerce sites.
 */
public interface ScraperStrategy {
    
    /**
     * Scrapes product data from a given URL.
     * @param url The URL of the product page.
     * @return A Product object containing the scraped data.
     * @throws IOException if an error occurs during scraping.
     */
    Product scrape(String url) throws IOException;

    /**
     * Checks if this strategy can handle the given URL.
     * @param url The URL to check.
     * @return true if the strategy can handle the URL, false otherwise.
     */
    boolean canHandle(String url);
}
