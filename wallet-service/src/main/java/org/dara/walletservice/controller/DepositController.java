package org.dara.walletservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dara.cryptosecurity.model.CurrentUser;
import org.dara.walletservice.dto.DepositRequest;
import org.dara.walletservice.dto.DepositResponse;
import org.dara.walletservice.dto.ErrorResponse;
import org.dara.walletservice.service.DepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wallet")
@RestController
@RequiredArgsConstructor
public class DepositController {

    private final DepositService depositService;

    @Operation(
            summary = "deposit wallet balance",
            description = "Deposit wallet balance for the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deposit operation successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Wallet, asset, or wallet balance not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/deposit")
    public ResponseEntity<DepositResponse> deposit(@AuthenticationPrincipal CurrentUser currentUser,@Valid @RequestBody DepositRequest depositRequest) {
        return ResponseEntity.ok(depositService.deposit(currentUser.userUuid(), depositRequest));
    }
}
