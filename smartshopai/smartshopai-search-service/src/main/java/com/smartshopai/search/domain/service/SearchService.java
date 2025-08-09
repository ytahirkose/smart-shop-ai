package com.smartshopai.search.domain.service;

import com.smartshopai.search.domain.entity.SearchIndex;

import java.util.List;
import java.util.Map;

public interface SearchService {
    List<SearchIndex> search(String query, String category, String brand,
                             Double minPrice, Double maxPrice,
                             String sortBy, String sortOrder,
                             Integer page, Integer size,
                             Map<String, Object> filters);

    List<SearchIndex> searchByCategory(String category, String query, String brand,
                                       Double minPrice, Double maxPrice,
                                       String sortBy, String sortOrder,
                                       Integer page, Integer size);

    List<SearchIndex> searchByBrand(String brand, String query, String category,
                                    Double minPrice, Double maxPrice,
                                    String sortBy, String sortOrder,
                                    Integer page, Integer size);

    void indexProduct(String productId, String name, String description, String brand,
                      String category, Double price, Double rating, String imageUrl,
                      Map<String, Object> attributes);

    void removeFromIndex(String productId);

    void updateIndex(String productId, String name, String description, String brand,
                     String category, Double price, Double rating, String imageUrl,
                     Map<String, Object> attributes);

    void reindexAll();

    Map<String, Object> getSearchStatistics();
}


