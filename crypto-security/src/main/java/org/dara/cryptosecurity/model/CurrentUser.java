package org.dara.cryptosecurity.model;

import java.util.UUID;

public record CurrentUser(
        UUID userUuid,
        String username,
        String email
) {}
