package org.dara.walletservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.dara.cryptosecurity.model.CurrentUser;
import org.dara.walletservice.dto.ErrorResponse;
import org.dara.walletservice.dto.WalletBalanceResponse;
import org.dara.walletservice.dto.WalletResponse;
import org.dara.walletservice.exception.WalletNotFoundException;
import org.dara.walletservice.mapper.WalletMapper;
import org.dara.walletservice.model.Wallet;
import org.dara.walletservice.model.WalletBalance;
import org.dara.walletservice.service.WalletBalanceService;
import org.dara.walletservice.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final WalletBalanceService walletBalanceService;
    private final WalletMapper walletMapper;

    @Operation(
            summary = "Get current user`s wallet",
            description = "Returns the wallet and balance of the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wallet retrieved successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Wallet not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public ResponseEntity<WalletResponse> getMyWallet(@AuthenticationPrincipal CurrentUser currentUser) {
        UUID userUuid = currentUser.userUuid();
        Wallet wallet = walletService.findWalletByUserUuid(userUuid).orElseThrow(() -> new WalletNotFoundException("Wallet not found"));
        return ResponseEntity.ok(walletMapper.walletToWalletResponse(wallet));
    }


    @Operation(
            summary = "Get current user's balance",
            description = "Returns the balance of a specific asset for the currently authenticated user."
    )
    @Parameter(
            name = "symbol",
            description = "Asset symbol",
            example = "BTC",
            required = true
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Balance retrieved successfully"
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
    @GetMapping("/me/balances/{symbol}")
    public ResponseEntity<WalletBalanceResponse> getMyWalletBalance(@AuthenticationPrincipal CurrentUser currentUser, @PathVariable String symbol) {
        WalletBalance walletBalance = walletBalanceService.findBalance(currentUser.userUuid(), symbol);
        return ResponseEntity.ok(walletMapper.walletBalanceToWalletBalanceResponse(walletBalance));
    }
}
