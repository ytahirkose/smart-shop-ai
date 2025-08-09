package com.smartshopai.user.presentation.controller;

import com.smartshopai.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PutMapping("/{userId}/grant-premium")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> grantPremiumRole(@PathVariable String userId) {
        userService.grantPremiumRole(userId);
        return ResponseEntity.ok().build();
    }
}
