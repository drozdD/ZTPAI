package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> req) {
        User user = new User();
        user.setUsername(req.get("username"));
        user.setPassword(passwordEncoder.encode(req.get("password")));
        user.setRoles(Set.of("ROLE_USER"));
        repository.save(user);
        return "Użytkownik zarejestrowany";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {
        User user = repository.findByUsername(req.get("username"))
                .orElseThrow(() -> new RuntimeException("Błędny login lub hasło"));

        if (!passwordEncoder.matches(req.get("password"), user.getPassword())) {
            throw new RuntimeException("Błędny login lub hasło");
        }

        var authorities = user.getRoles().stream()
                .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                .toList();

        String token = jwtService.generateToken(user.getUsername(), authorities);
        return Map.of("token", token);
    }
}