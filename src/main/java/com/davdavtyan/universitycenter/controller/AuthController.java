package com.davdavtyan.universitycenter.controller;

import com.davdavtyan.universitycenter.UserRepository;
import com.davdavtyan.universitycenter.dto.request.LoginRequest;
import com.davdavtyan.universitycenter.dto.request.UserRegisterRequest;
import com.davdavtyan.universitycenter.entity.Role;
import com.davdavtyan.universitycenter.entity.User;
import com.davdavtyan.universitycenter.service.JwtService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AuthController(AuthenticationManager authManager, JwtService jwtService, UserRepository userRepository,
                          PasswordEncoder encoder) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping("/register")
//    @PreAuthorize("hasRole('ADMIN')")
    public String register(@RequestBody UserRegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Error: Email is already registered!";
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setName(request.getName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encoder.encode(request.getPassword()));

        Role userRole = Role.valueOf(request.getRole().toUpperCase());
        newUser.setRole(userRole);

        userRepository.save(newUser);
        return "User registered successfully with role: " + userRole;

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 1. Проверяем логин и пароль
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getEmail());

        Map<String, String> stringStringMap = Map.of(
            "token", token,
            "role", user.getRole().name()
        );
        return ResponseEntity.ok(stringStringMap);
    }

}