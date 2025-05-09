package com.example.project.dto;

import com.example.project.validation.annotations.Password;
import com.example.project.validation.annotations.Username;

public record AuthDto
        (
                @Username(allowEmpty = false)
                String username,
                @Password(allowEmpty = false)
                String password
        )
{
}
