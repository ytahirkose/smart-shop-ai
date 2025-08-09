package com.smartshopai.aisearch.presentation.controller;

import com.smartshopai.aisearch.domain.service.SearchService;
import com.smartshopai.aisearch.infrastructure.dto.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ai/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<Product>> search(@RequestParam("query") String query) {
        List<Product> products = searchService.semanticSearch(query);
        return ResponseEntity.ok(products);
    }
}
