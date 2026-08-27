package org.dara.authenticationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.dto.*;
import org.dara.authenticationservice.service.AuthService;
import org.dara.authenticationservice.service.EmailVerificationTokenService;
import org.dara.authenticationservice.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.FlashMapManager;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authUService;
    private final RefreshTokenService refreshTokenService;
    private final EmailVerificationTokenService emailVerificationTokenService;
    private final FlashMapManager flashMapManager;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(authUService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception{
        return ResponseEntity.ok(refreshTokenService.refresh(refreshTokenRequest));
    }

    @GetMapping("verify-email")
    public ResponseEntity<VerificationEmailResponse> verifyEmail(@RequestParam String token){
        emailVerificationTokenService.verifyToken(token);
        return ResponseEntity.ok(new VerificationEmailResponse(null, true));
    }
}
