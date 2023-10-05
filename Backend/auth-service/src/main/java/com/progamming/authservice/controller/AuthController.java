package com.progamming.authservice.controller;

import com.progamming.authservice.entity.UserCredential;
import com.progamming.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authManager;
    @PostMapping("/token")
    public String getToken(UserCredential userCredential) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(userCredential.getUserName(), userCredential.getPassword()));
        if(authentication.isAuthenticated()) {
            return authService.generateToken(userCredential.getUserName());
        } else {
            throw new RuntimeException("invalid access");
        }

    }
    @GetMapping("/validate")
    public String validateToken(@RequestParam String token) {
        authService.validateToken(token);
        return "Token is valid";
    }

}
