package com.shopmarket.spring_boot_shopmarket.auth.controller;

import com.shopmarket.spring_boot_shopmarket.auth.dto.CurrentUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController
@RequestMapping("/api/me")
public class MeController {

    @GetMapping
    public CurrentUserDto me(@AuthenticationPrincipal Jwt jwt, Authentication authentication) {
        return new CurrentUserDto(
                jwt.getSubject(),
                jwt.getClaimAsString("preferred_username"),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList()
        );
    }
}