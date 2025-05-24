package com.example.project.controller.authController;

import com.example.project.dto.responseDto.auth.AuthDto;
import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.pojo.JwtResponse;
import com.example.project.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private AuthenticationService userService;

    public AuthController(AuthenticationService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login
            (
                    @Valid
                    @RequestBody
                    AuthDto dto
            ) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @PostMapping("/registration")
    public ResponseEntity<ResponseMessage> registration
            (
                    @Valid
                    @RequestBody
                    RegistrationDto registrationDto
            ) {
        return ResponseEntity.created(URI.create("/api/user/my-profile")).body(userService.registration(registrationDto));
    }
}
