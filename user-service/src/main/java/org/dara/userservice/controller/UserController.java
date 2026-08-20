package org.dara.userservice.controller;

import org.dara.userservice.dto.UserRequestDto;
import org.dara.userservice.dto.UserResponseDto;
import org.dara.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allUsers")
    public ResponseEntity<List<UserResponseDto>> gatAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/me")
    public ResponseEntity<UserResponseDto> updateCurrentUser(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.updateCurrentUser(userRequestDto));
    }
}
