package org.dara.authenticationservice.dto;

import java.util.UUID;

public record AuthResponse(
        Long userId,
        String username,
        String userUUID,
        String accessToken,
        String refreshToken,
        String tokenType
) {}
