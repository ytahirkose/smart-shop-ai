package com.smartshopai.ai.search.domain.repository;

import com.smartshopai.ai.search.domain.entity.SearchQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchQueryRepository extends MongoRepository<SearchQuery, String> {
    
    List<SearchQuery> findByUserId(String userId);
    
    List<SearchQuery> findBySearchType(String searchType);
    
    @Query("{'userId': ?0, 'createdAt': {$gte: ?1}}")
    List<SearchQuery> findByUserIdAndCreatedAfter(String userId, LocalDateTime after);
    
    @Query("{'queryText': {$regex: ?0, $options: 'i'}}")
    List<SearchQuery> findByQueryTextContaining(String queryText);
    
    @Query("{'isSuccessful': true, 'searchTime': {$lte: ?0}}")
    List<SearchQuery> findFastQueries(Double maxSearchTime);
    
    @Query("{'userId': ?0, 'searchType': ?1}")
    List<SearchQuery> findByUserIdAndSearchType(String userId, String searchType);
}
