package com.shopmarket.spring_boot_shopmarket.auth.dto;

import java.util.List;

public record CurrentUserDto(String userId, String username, List<String> roles) {
}