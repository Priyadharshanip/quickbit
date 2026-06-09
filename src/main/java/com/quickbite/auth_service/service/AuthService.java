package com.quickbite.auth_service.service;

import com.quickbite.auth_service.config.JwtUtil;
import com.quickbite.auth_service.model.LoginRequest;
import com.quickbite.auth_service.model.LoginResponse;
import com.quickbite.auth_service.model.User;
import com.quickbite.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Check if password matches the stored hash
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3. Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        // 4. Return token + user info
        return new LoginResponse(token, user.getEmail(), user.getRole());
    }
}