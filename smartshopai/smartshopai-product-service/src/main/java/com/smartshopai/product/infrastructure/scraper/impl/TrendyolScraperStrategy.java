package com.smartshopai.product.infrastructure.scraper.impl;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.smartshopai.product.domain.entity.Product;
import com.smartshopai.product.domain.entity.ProductSpecifications;
import com.smartshopai.product.infrastructure.scraper.PlaywrightService;
import com.smartshopai.product.infrastructure.scraper.ScraperStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

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
        try (BrowserContext context = playwrightService.getBrowser().newContext()) {
            Page page = context.newPage();
            page.navigate(url);

            // Wait for the main product container to ensure the page is loaded
            page.waitForSelector("div.product-container", new Page.WaitForSelectorOptions().setTimeout(10000));

            String name = page.locator("h1.pr-new-br").innerText();
            String priceText = page.locator("div.product-price-container span.prc-dsc").innerText();
            String imageUrl = page.locator("div.base-product-image img").first().getAttribute("src");
            String description = page.locator("div.detail-desc-list").innerText();

            Map<String, String> specifications = page.locator("ul.detail-attr-container li.detail-attr-item")
                    .all()
                    .stream()
                    .collect(Collectors.toMap(
                            spec -> spec.locator("span").first().innerText(),
                            spec -> spec.locator("span").last().innerText(),
                            (first, second) -> first // In case of duplicate keys
                    ));

            BigDecimal price = new BigDecimal(priceText.replaceAll("[^\\d,]", "").replace(",", "."));

            log.info("Scraped product: {}, Price: {}, Image: {}", name, price, imageUrl);

            ProductSpecifications specs = new ProductSpecifications();
            specs.setSpecifications(specifications);

            return Product.builder()
                    .name(name)
                    .description(description)
                    .productUrl(url)
                    .mainImageUrl(imageUrl)
                    .currentPrice(price)
                    .specifications(specs)
                    .currency("TRY")
                    .build();
        } catch (Exception e) {
            log.error("Failed to scrape Trendyol URL: {}", url, e);
            return null;
        }
    }
}
