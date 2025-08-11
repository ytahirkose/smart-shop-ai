package com.smartshopai.product.infrastructure.scraper;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



/**
 * Service for managing Playwright browser instances
 * Handles web scraping operations for product data collection
 */
@Slf4j
@Service
public class PlaywrightService {

    private Playwright playwright;
    private Browser browser;
    
    @Value("${playwright.headless:true}")
    private boolean headless;
    
    @Value("${playwright.timeout:30000}")
    private int timeout;

    @PostConstruct
    void initialize() {
        try {
            log.info("Initializing Playwright service...");
            this.playwright = Playwright.create();
            
            // Configure browser options
            this.browser = playwright.chromium().launch();
            log.info("Playwright service initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize Playwright service", e);
            throw new RuntimeException("Playwright initialization failed", e);
        }
    }

    @PreDestroy
    void cleanup() {
        try {
            if (browser != null) {
                browser.close();
                log.info("Browser closed");
            }
            if (playwright != null) {
                playwright.close();
                log.info("Playwright closed");
            }
        } catch (Exception e) {
            log.error("Error during Playwright cleanup", e);
        }
    }

    public Browser getBrowser() {
        return browser;
    }
    
    /**
     * Create a new browser context with default settings
     */
    public BrowserContext createContext() {
        return browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .setViewportSize(1920, 1080)
                .setLocale("tr-TR")
                .setTimezoneId("Europe/Istanbul"));
    }
    
    /**
     * Create a new page with timeout and error handling
     */
    public Page createPage(BrowserContext context) {
        Page page = context.newPage();
        page.setDefaultTimeout(timeout);
        return page;
    }
    
    /**
     * Execute scraping task with automatic cleanup
     */
    public <T> T executeScrapingTask(ScrapingTask<T> task) {
        BrowserContext context = null;
        Page page = null;
        
        try {
            context = createContext();
            page = createPage(context);
            return task.execute(page);
        } catch (Exception e) {
            log.error("Error during scraping task execution", e);
            throw new RuntimeException("Scraping task failed", e);
        } finally {
            if (page != null) {
                page.close();
            }
            if (context != null) {
                context.close();
            }
        }
    }
    
    /**
     * Functional interface for scraping tasks
     */
    @FunctionalInterface
    public interface ScrapingTask<T> {
        T execute(Page page) throws Exception;
    }
}
