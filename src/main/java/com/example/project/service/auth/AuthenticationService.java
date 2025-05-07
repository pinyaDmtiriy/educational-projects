package com.example.project.service.auth;

import com.example.project.dto.AuthDto;
import com.example.project.dto.RegistrationUserDto;
import com.example.project.pojo.JwtResponse;

public interface AuthenticationService {
    void registration(RegistrationUserDto user);
    JwtResponse login(AuthDto dto);
}
