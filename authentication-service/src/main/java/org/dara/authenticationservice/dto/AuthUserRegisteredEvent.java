package org.dara.authenticationservice.dto;

import java.util.UUID;

public record AuthUserRegisteredEvent(
        UUID userUUID,
        String username
) {}
