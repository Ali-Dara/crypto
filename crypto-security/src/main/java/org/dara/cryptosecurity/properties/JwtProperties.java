package org.dara.cryptosecurity.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "crypto.security.jwt")
public record JwtProperties(
        String secretKey
) {}
