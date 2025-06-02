package org.act.houserentingsystem.service;

import lombok.RequiredArgsConstructor;
import org.act.houserentingsystem.dto.ApiResponse;
import org.act.houserentingsystem.dto.AuthResponse;
import org.act.houserentingsystem.dto.LoginRequest;
import org.act.houserentingsystem.dto.RegisterRequest;
import org.act.houserentingsystem.entity.User;
import org.act.houserentingsystem.exception.UserAlreadyExistsException;
import org.act.houserentingsystem.exception.UserNotFoundException;
import org.act.houserentingsystem.exception.InvalidCredentialsException;
import org.act.houserentingsystem.repository.UserRepository;
import org.act.houserentingsystem.security.jwt.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ApiResponse<String> register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with username: " + request.getUsername());
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : "ROLE_USER")
                .build();

        userRepository.save(user);

        return ApiResponse.<String>builder()
                .success(true)
                .message("User registered successfully!")
                .data(null)
                .build();
    }

    public ApiResponse<AuthResponse> login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + request.getUsername()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        AuthResponse authResponse = new AuthResponse(token);

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login successful!")
                .data(authResponse)
                .build();
    }
}
