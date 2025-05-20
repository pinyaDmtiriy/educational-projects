package com.example.project.service.auth;

import com.example.project.dto.AuthDto;
import com.example.project.dto.RegistrationUserDto;
import com.example.project.entity.User;
import com.example.project.pojo.JwtResponse;
import com.example.project.security.util.JwtUtil;
import com.example.project.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;
    private AuthenticationManager authentication;
    private JwtUtil jwtUtil;

    public AuthenticationServiceImpl(UserService userService, AuthenticationManager authentication, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authentication = authentication;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void registration(RegistrationUserDto user) {
        userService.create(user);
    }

    @Override
    public JwtResponse login(AuthDto dto) {
        Authentication auth = authentication.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));

        User user = (User) auth.getPrincipal();

        userService.checkStatus(user);

        return JwtResponse.login(jwtUtil.generateToken(user), user);
    }
}
