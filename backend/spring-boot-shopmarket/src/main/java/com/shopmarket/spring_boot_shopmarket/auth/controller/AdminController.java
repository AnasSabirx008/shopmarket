package com.shopmarket.spring_boot_shopmarket.auth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/ping")
    public Map<String, String> ping(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("message", "Hello admin " + jwt.getClaimAsString("preferred_username"));
    }
}