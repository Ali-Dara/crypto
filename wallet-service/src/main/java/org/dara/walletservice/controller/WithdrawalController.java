package org.dara.walletservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dara.cryptosecurity.model.CurrentUser;
import org.dara.walletservice.dto.ErrorResponse;
import org.dara.walletservice.dto.WithdrawalRequest;
import org.dara.walletservice.dto.WithdrawalResponse;
import org.dara.walletservice.service.WithdrawalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WithdrawalController {
    
    private final WithdrawalService service;

    @Operation(
            summary = "withdrawal wallet balance",
            description = "withdrawal wallet balance for the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "withdrawal operation successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid amount or insufficient available balance",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
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
    @PostMapping("/withdrawal")
    public ResponseEntity<WithdrawalResponse> withdrawal(@AuthenticationPrincipal CurrentUser currentUser, @Valid @RequestBody WithdrawalRequest request) {
        return ResponseEntity.ok(service.withdraw(currentUser.userUuid(), request));
    }
}
