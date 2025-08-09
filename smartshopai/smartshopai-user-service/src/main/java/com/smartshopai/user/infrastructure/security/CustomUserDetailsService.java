package com.smartshopai.user.infrastructure.security;

import com.smartshopai.user.domain.entity.User;
import com.smartshopai.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Custom UserDetailsService implementation for User Service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);
        
        try {
            User user = userService.findByUsernameOrEmail(username);
            
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRoles().stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()))
                    .accountExpired(!user.isAccountNonExpired())
                    .accountLocked(!user.isAccountNonLocked())
                    .credentialsExpired(!user.isCredentialsNonExpired())
                    .disabled(!user.isEnabled())
                    .build();
                    
        } catch (Exception e) {
            log.error("Error loading user by username: {}", username, e);
            throw new UsernameNotFoundException("User not found with username: " + username, e);
        }
    }
}
