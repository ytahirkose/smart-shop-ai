package com.smartshopai.user.presentation.controller;

import com.smartshopai.user.application.service.UserService;
import com.smartshopai.user.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User management operations")
public class UserController {
    
    private final UserService userService;
    
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Creating new user with username: {}", user.getUsername());
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique identifier")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        log.info("Getting user by ID: {}", id);
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Retrieves a user by their username")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        log.info("Getting user by username: {}", username);
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieves a user by their email address")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        log.info("Getting user by email: {}", email);
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users in the system")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Getting all users");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Retrieves all users with a specific role")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        log.info("Getting users by role: {}", role);
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/category/{category}")
    @Operation(summary = "Get users by preferred category", description = "Retrieves users who prefer a specific category")
    public ResponseEntity<List<User>> getUsersByPreferredCategory(@PathVariable String category) {
        log.info("Getting users by preferred category: {}", category);
        List<User> users = userService.getUsersByPreferredCategory(category);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/budget/{budget}")
    @Operation(summary = "Get users by budget limit", description = "Retrieves users with budget limit greater than specified amount")
    public ResponseEntity<List<User>> getUsersByBudgetLimit(@PathVariable Double budget) {
        log.info("Getting users by budget limit: {}", budget);
        List<User> users = userService.getUsersByBudgetLimit(budget);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/quality/{quality}")
    @Operation(summary = "Get users by quality preference", description = "Retrieves users with a specific quality preference")
    public ResponseEntity<List<User>> getUsersByQualityPreference(@PathVariable String quality) {
        log.info("Getting users by quality preference: {}", quality);
        List<User> users = userService.getUsersByQualityPreference(quality);
        return ResponseEntity.ok(users);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user's information")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User userDetails) {
        log.info("Updating user with ID: {}", id);
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user from the system")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/login")
    @Operation(summary = "Update last login", description = "Updates the last login information for a user")
    public ResponseEntity<Void> updateLastLogin(@PathVariable String id, @RequestParam String ipAddress) {
        log.info("Updating last login for user with ID: {}", id);
        userService.updateLastLogin(id, ipAddress);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{id}/verify-email")
    @Operation(summary = "Verify email", description = "Marks a user's email as verified")
    public ResponseEntity<Boolean> verifyEmail(@PathVariable String id) {
        log.info("Verifying email for user with ID: {}", id);
        boolean verified = userService.verifyEmail(id);
        return ResponseEntity.ok(verified);
    }
    
    @PutMapping("/{id}/verify-phone")
    @Operation(summary = "Verify phone", description = "Marks a user's phone number as verified")
    public ResponseEntity<Boolean> verifyPhone(@PathVariable String id) {
        log.info("Verifying phone for user with ID: {}", id);
        boolean verified = userService.verifyPhone(id);
        return ResponseEntity.ok(verified);
    }
    
    @PutMapping("/{id}/change-password")
    @Operation(summary = "Change password", description = "Changes a user's password")
    public ResponseEntity<Void> changePassword(@PathVariable String id, @RequestParam String newPassword) {
        log.info("Changing password for user with ID: {}", id);
        userService.changePassword(id, newPassword);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Simple health check endpoint")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("User Service is running!");
    }
}
