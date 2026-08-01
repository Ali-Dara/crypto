package org.dara.apigateway.model;

import java.util.Set;

public record CurrentUser(
        Long userId,
        String username,
        String email,
        Set<String> roles,
        Set<String> permissions

) {}
