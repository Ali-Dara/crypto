package org.dara.cryptoevent.Dto;

import java.util.UUID;

public record EmailVerificationRequestedEvent(
        UUID userUuid,
        String email,
        String username,
        String verificationToken
) {}
