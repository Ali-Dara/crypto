package org.dara.cryptoevent.Dto;

import java.util.UUID;

public record AuthUserRegisteredEvent(
        UUID userUUID,
        String username
) {}

