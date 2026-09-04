package org.dara.walletservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API error response")
public record ErrorResponse(
        boolean success,
        String message
) {}
