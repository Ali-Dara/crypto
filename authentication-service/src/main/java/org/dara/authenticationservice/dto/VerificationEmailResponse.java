package org.dara.authenticationservice.dto;

import java.util.UUID;

public record VerificationEmailResponse(
        String verificationEmailResult,
        boolean verificationEmailStatus
) {}
