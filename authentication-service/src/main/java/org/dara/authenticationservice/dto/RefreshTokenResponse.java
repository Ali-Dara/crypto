package org.dara.authenticationservice.dto;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}
