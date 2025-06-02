package org.act.houserentingsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.act.houserentingsystem.dto.ApiResponse;
import org.act.houserentingsystem.dto.AuthResponse;
import org.act.houserentingsystem.dto.LoginRequest;
import org.act.houserentingsystem.dto.RegisterRequest;
import org.act.houserentingsystem.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
