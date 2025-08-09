package com.smartshopai.user.domain.repository;

import com.smartshopai.user.domain.entity.UserPreferences;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPreferencesRepository extends MongoRepository<UserPreferences, String> {
    
    Optional<UserPreferences> findByUserId(String userId);
    
    List<UserPreferences> findByAiRecommendationsEnabled(boolean enabled);
    
    List<UserPreferences> findByAiBehaviorAnalysisEnabled(boolean enabled);
    
    List<UserPreferences> findByAiPersonalizationEnabled(boolean enabled);
    
    List<UserPreferences> findByDataSharingEnabled(boolean enabled);
    
    List<UserPreferences> findByPersonalizedAdsEnabled(boolean enabled);
    
    List<UserPreferences> findByPreferencesCompleted(boolean completed);
    
    boolean existsByUserId(String userId);
}
