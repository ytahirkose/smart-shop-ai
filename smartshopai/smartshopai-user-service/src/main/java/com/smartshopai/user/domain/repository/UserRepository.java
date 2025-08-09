package com.smartshopai.user.domain.repository;

import com.smartshopai.user.domain.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity
 * Provides data access methods for user operations
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find user by email or username
     */
    Optional<User> findByEmailOrUsername(String email, String username);
    
    /**
     * Check if user exists by email
     */
    boolean existsByEmail(String email);
    
    /**
     * Check if user exists by username
     */
    boolean existsByUsername(String username);
    
    /**
     * Find users by role
     */
    List<User> findByRolesContaining(String role);
    
    /**
     * Find enabled users
     */
    List<User> findByEnabledTrue();
    
    /**
     * Find users by enabled status
     */
    List<User> findByEnabled(boolean enabled);
    
    /**
     * Find users by preferred categories
     */
    @Query("{'preferredCategories': {$in: ?0}}")
    List<User> findByPreferredCategoriesIn(List<String> categories);
    
    /**
     * Find users with budget greater than or equal to
     */
    @Query("{'maxBudget': {$gte: ?0}}")
    List<User> findByMaxBudgetGreaterThanEqual(Double budget);
    
    /**
     * Find users by shopping preferences
     */
    @Query("{'shoppingPreferences': {$regex: ?0, $options: 'i'}}")
    List<User> findByShoppingPreferencesContaining(String preference);
    
    /**
     * Find users by email verification status
     */
    List<User> findByEmailVerified(boolean emailVerified);
    
    /**
     * Find users by phone verification status
     */
    List<User> findByPhoneVerified(boolean phoneVerified);
    
    /**
     * Find users created after specific date
     */
    List<User> findByCreatedAtAfter(java.time.LocalDateTime date);
    
    /**
     * Find users by last login date
     */
    List<User> findByLastLoginAtAfter(java.time.LocalDateTime date);
    
    /**
     * Count users by enabled status
     */
    long countByEnabled(boolean enabled);
    
    /**
     * Count users by email verification status
     */
    long countByEmailVerified(boolean emailVerified);
    
    /**
     * Find users by max budget less than or equal to
     */
    @Query("{'maxBudget': {$lte: ?0}}")
    List<User> findByMaxBudgetLessThanEqual(Double maxBudget);
    
    /**
     * Find users by preferred categories and max budget
     */
    @Query("{'preferredCategories': {$in: ?0}, 'maxBudget': {$lte: ?1}}")
    List<User> findByPreferredCategoriesInAndMaxBudgetLessThanEqual(List<String> categories, Double maxBudget);
}
