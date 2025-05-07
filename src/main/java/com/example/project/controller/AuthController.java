package com.example.project.controller;

import com.example.project.dto.AuthDto;
import com.example.project.dto.RegistrationUserDto;
import com.example.project.pojo.JwtResponse;
import com.example.project.service.auth.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationService userService;

    public AuthController(AuthenticationService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@RequestBody RegistrationUserDto authDto) {
        userService.registration(authDto);
        return ResponseEntity.created(URI.create("/api/user/my-profile")).build();
    }
}
