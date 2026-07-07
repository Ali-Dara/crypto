package org.dara.authenticationservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secretKey,
        long accessTokenExpiration,
        long refreshTokenExpiration
) {}
