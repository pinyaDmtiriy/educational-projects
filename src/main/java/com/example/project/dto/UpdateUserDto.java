package com.example.project.dto;

import com.example.project.validation.annotations.Password;
import com.example.project.validation.annotations.Username;
import jakarta.validation.constraints.Email;

public record UpdateUserDto
        (
                @Username(allowEmpty = true)
                String username,
                @Email
                String firstEmail,
                @Password(allowEmpty = true)
                String password
        )
{
}
