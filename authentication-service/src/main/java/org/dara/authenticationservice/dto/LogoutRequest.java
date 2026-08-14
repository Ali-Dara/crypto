package org.dara.authenticationservice.dto;

public record LogoutRequest(
    String refreshToken
) {}
