package org.dara.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.dara.authenticationservice.dto.AuthResponse;
import org.dara.authenticationservice.dto.RegisterRequest;
import org.dara.authenticationservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registering")
@RequiredArgsConstructor
public class RegisterController {

    private final AuthService authUService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok().body(authUService.register(registerRequest));
    }
}
