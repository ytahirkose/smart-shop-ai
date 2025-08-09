package com.smartshopai.user.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    private Set<String> roles;

    @Builder.Default
    private boolean enabled = true;
    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @DBRef(lazy = true)
    private UserProfile userProfile;

    @DBRef(lazy = true)
    private UserPreferences userPreferences;

    @DBRef(lazy = true)
    private UserBehaviorMetrics userBehaviorMetrics;

    // --- ADDED FIELDS FOR REPOSITORY COMPATIBILITY ---
    /**
     * Kullanıcının tercih ettiği kategoriler (AI öneri ve arama için)
     */
    private Set<String> preferredCategories;

    /**
     * Kullanıcının maksimum alışveriş bütçesi (AI öneri ve arama için)
     */
    private Double maxBudget;

    /**
     * Kullanıcının alışveriş tercihleri (AI öneri ve arama için)
     */
    private String shoppingPreferences;

    /**
     * Kullanıcının e-posta adresi doğrulandı mı?
     */
    @Builder.Default
    private boolean emailVerified = false;

    /**
     * Kullanıcının telefon numarası doğrulandı mı?
     */
    @Builder.Default
    private boolean phoneVerified = false;

    /**
     * Son giriş zamanı (login tracking)
     */
    private LocalDateTime lastLoginAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
