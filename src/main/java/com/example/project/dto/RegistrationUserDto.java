package com.example.project.dto;

import com.example.project.validation.annotations.Password;
import com.example.project.validation.annotations.Username;

public record RegistrationUserDto
        (
                @Username
                String username,
                @Username
                String firstEmail,
                @Password
                String password
        )
{
}
