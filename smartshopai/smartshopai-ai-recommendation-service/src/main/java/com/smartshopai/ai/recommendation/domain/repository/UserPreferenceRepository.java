package com.smartshopai.ai.recommendation.domain.repository;

import com.smartshopai.ai.recommendation.domain.entity.UserPreference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPreferenceRepository extends MongoRepository<UserPreference, String> {
    
    Optional<UserPreference> findByUserId(String userId);
    
    List<UserPreference> findByActive(boolean active);
    
    List<UserPreference> findByPersonalizedRecommendations(boolean personalized);
    
    List<UserPreference> findByRealTimeRecommendations(boolean realTime);
    
    List<UserPreference> findByDataSharingEnabled(boolean enabled);
    
    List<UserPreference> findByPreferencesCompleted(boolean completed);
    
    List<UserPreference> findByAiAnalysisCompleted(boolean completed);
    
    boolean existsByUserId(String userId);
}
