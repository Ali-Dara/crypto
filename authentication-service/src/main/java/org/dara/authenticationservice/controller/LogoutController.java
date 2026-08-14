package org.dara.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.dto.LogoutRequest;
import org.dara.authenticationservice.dto.RefreshTokenRequest;
import org.dara.authenticationservice.service.AuthService;
import org.dara.authenticationservice.service.RefreshTokenService;
import org.dara.authenticationservice.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LogoutController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequest logoutRequest) throws Exception {
        Long userId = SecurityUtils.getCurrentUser().userId();
        refreshTokenService.logout(userId, logoutRequest.refreshToken());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logoutAllSession")
    public ResponseEntity<Void> logoutAllSession(@RequestBody LogoutRequest logoutRequest) throws Exception {
        Long userId = SecurityUtils.getCurrentUser().userId();
        refreshTokenService.logoutAllSession(userId, logoutRequest.refreshToken());
        return ResponseEntity.noContent().build();
    }
}
