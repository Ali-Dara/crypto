package org.dara.authenticationservice.model;

public record CurrentUser(
        Long userId,
        String username
) {}
