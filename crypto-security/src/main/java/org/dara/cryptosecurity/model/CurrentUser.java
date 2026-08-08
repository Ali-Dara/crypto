package org.dara.cryptosecurity.model;

import java.util.UUID;

public record CurrentUser(
        UUID userId,
        String username,
        String email
) {}
