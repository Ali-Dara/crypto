package org.dara.apigateway.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class SecurityTestController {

    @GetMapping("/authenticated")
    public String authenticate(String username, String password) {
        return "Authenticated";
    }

    @PreAuthorize("hasAllAuthorities('permission:admin')")
    @GetMapping("/admin")
    public String admin(){
        return "Admin";
    }
}
