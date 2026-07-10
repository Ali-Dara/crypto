package org.dara.authenticationservice.dto;

public record AuthResponse(
        Long userId,
        String username,
        String accessToken,
        String refreshToken,
        String tokenType
) {}
