package com.smartshopai.airecommendation.infrastructure.client;

import com.smartshopai.airecommendation.infrastructure.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "smartshopai-user-service")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/{userId}")
    UserDto getUserById(@PathVariable("userId") String userId);
}
