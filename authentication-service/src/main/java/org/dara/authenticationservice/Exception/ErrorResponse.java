package org.dara.authenticationservice.Exception;

public record ErrorResponse(
        boolean success,
        String message
) {}
