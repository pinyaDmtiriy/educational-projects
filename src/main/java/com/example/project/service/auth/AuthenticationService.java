package com.example.project.service.auth;

import com.example.project.dto.responseDto.auth.AuthDto;
import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.pojo.JwtResponse;

public interface AuthenticationService {
    ResponseMessage registration(RegistrationDto user);
    JwtResponse login(AuthDto dto);
}
