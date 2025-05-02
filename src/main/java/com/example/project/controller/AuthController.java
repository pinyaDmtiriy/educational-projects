package com.example.project.controller;

import com.example.project.dto.AuthDto;
import com.example.project.entity.User;
import com.example.project.security.util.JwtUtil;
import com.example.project.security.service.CustomUserDetailsService;
import com.example.project.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authentication;
    private UserService userService;
    private JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authentication, UserService userService, JwtUtil jwtUtil) {
        this.authentication = authentication;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(AuthDto dto) {
        Authentication auth = authentication.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
        User user = (User) auth.getPrincipal();

        userService.checkStatus(user);

        return jwtUtil.getToken(user);
    }

    @PostMapping("/registration")
    public void registration(AuthDto authDto) {
        userService.create(authDto);
    }
}
