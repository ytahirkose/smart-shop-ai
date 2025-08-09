package com.smartshopai.user.domain.service;

import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.entity.UserProfile;
import com.smartshopai.user.domain.entity.UserPreferences;
import com.smartshopai.user.domain.entity.UserBehaviorMetrics;
import com.smartshopai.user.domain.event.UserEventPublisher;
import com.smartshopai.user.domain.repository.UserProfileRepository;
import com.smartshopai.user.domain.repository.UserRepository;
import com.smartshopai.user.domain.repository.UserPreferencesRepository;
import com.smartshopai.user.presentation.exception.UserAlreadyExistsException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private static final int MAX_HISTORY_SIZE = 20;
    
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserPreferencesRepository userPreferencesRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserEventPublisher userEventPublisher;
    
    @Transactional
    public User createUser(User user, UserProfile userProfile) {
        log.info("Attempting to create a new user with username: {}", user.getUsername());

        if (userRepository.findByUsername(user.getUsername()).isPresent() || userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("A user with this username or email already exists.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        
        userProfile.setCreatedAt(now);
        userProfile.setUpdatedAt(now);
        UserProfile savedProfile = userProfileRepository.save(userProfile);

        UserPreferences preferences = new UserPreferences();
        preferences.setCreatedAt(now);
        preferences.setUpdatedAt(now);
        UserPreferences savedPreferences = userPreferencesRepository.save(preferences);
        
        user.setUserProfile(savedProfile);
        user.setUserPreferences(savedPreferences);
        
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());

        userEventPublisher.publishUserCreatedEvent(
                com.smartshopai.user.domain.event.UserCreatedEvent.builder()
                        .userId(savedUser.getId())
                        .username(savedUser.getUsername())
                        .email(savedUser.getEmail())
                        .createdAt(savedUser.getCreatedAt())
                        .build()
        );
        
        return savedUser;
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Username veya email ile kullanıcıyı bulur (Optional)
     */
    public Optional<User> getUserByIdOrEmail(String idOrEmail) {
        Optional<User> byId = userRepository.findById(idOrEmail);
        if (byId.isPresent()) return byId;
        return userRepository.findByEmail(idOrEmail);
    }

    /**
     * Kullanıcıya premium rolü tanımlar (Set, HashSet kullanımı)
     */
    @Transactional
    public void grantPremiumRole(String userId) {
        log.info("Attempting to grant PREMIUM role to user ID: {}", userId);
        User user = getUserById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Set<String> roles = new HashSet<>(user.getRoles());
        roles.add("ROLE_PREMIUM");
        user.setRoles(roles);
        userRepository.save(user);
        log.info("Successfully granted PREMIUM role to user ID: {}", userId);
    }

    /**
     * Kullanıcı ürün görüntüleme geçmişini günceller (List, sliding window)
     */
    @Transactional
    public void trackProductView(String userId, String productId) {
        User user = getUserById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        UserBehaviorMetrics metrics = user.getUserBehaviorMetrics();
        if (metrics == null) {
            metrics = new UserBehaviorMetrics();
            metrics.initializeDefaults();
            user.setUserBehaviorMetrics(metrics);
        }
        metrics.setTotalProductViews(metrics.getTotalProductViews() + 1);
        metrics.getViewedProducts().merge(productId, 1, Integer::sum);
        // Update recent views with sliding window
        List<String> recentViews = metrics.getRecentViewedProductIds();
        recentViews.remove(productId); // Remove if exists to move it to the front
        recentViews.add(0, productId); // Add to the front
        if (recentViews.size() > MAX_HISTORY_SIZE) {
            recentViews.remove(MAX_HISTORY_SIZE);
        }
        userRepository.save(user);
        log.debug("Tracked product view for user {} and product {}", userId, productId);
    }

    /**
     * Kullanıcı arama geçmişini günceller (List, sliding window)
     */
    @Transactional
    public void trackSearch(String userId, String searchQuery) {
        User user = getUserById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        UserBehaviorMetrics metrics = user.getUserBehaviorMetrics();
        if (metrics == null) {
            metrics = new UserBehaviorMetrics();
            metrics.initializeDefaults();
            user.setUserBehaviorMetrics(metrics);
        }
        metrics.setTotalSearches(metrics.getTotalSearches() + 1);
        metrics.getSearchKeywords().merge(searchQuery.toLowerCase(), 1, Integer::sum);
        // Update recent searches with sliding window
        List<String> recentSearches = metrics.getRecentSearchQueries();
        recentSearches.remove(searchQuery);
        recentSearches.add(0, searchQuery);
        if (recentSearches.size() > MAX_HISTORY_SIZE) {
            recentSearches.remove(MAX_HISTORY_SIZE);
        }
        userRepository.save(user);
        log.debug("Tracked search for user {} with query '{}'", userId, searchQuery);
    }

    // Additional helper methods referenced by application service ------------------

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findByEnabledTrue();
    }

    @Transactional
    public User updateUser(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserProfile updateUserProfile(String userId, UserProfile profileUpdate) {
        User user = getUserById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        profileUpdate.setId(user.getUserProfile() != null ? user.getUserProfile().getId() : null);
        profileUpdate.setUpdatedAt(LocalDateTime.now());
        UserProfile saved = userProfileRepository.save(profileUpdate);
        user.setUserProfile(saved);
        userRepository.save(user);
        return saved;
    }

    public UserProfile getUserProfile(String userId) {
        User user = getUserById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return user.getUserProfile();
    }

    // Dummy authentication placeholder (to be replaced by real SecurityService)
    public String authenticateUser(String email, String rawPassword) {
        // In real impl, verify credentials and generate JWT
        return "dummy-jwt-token";
    }
}
