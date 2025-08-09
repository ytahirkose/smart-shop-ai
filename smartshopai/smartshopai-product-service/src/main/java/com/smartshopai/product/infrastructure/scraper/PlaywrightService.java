package com.smartshopai.product.infrastructure.scraper;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class PlaywrightService {

    private Playwright playwright;
    private Browser browser;

    @PostConstruct
    void initialize() {
        this.playwright = Playwright.create();
        // For production, you might want to connect to a remote browser
        // or use a specific browser type like firefox() or webkit()
        this.browser = playwright.chromium().launch();
    }

    @PreDestroy
    void cleanup() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    public Browser getBrowser() {
        return browser;
    }
}
