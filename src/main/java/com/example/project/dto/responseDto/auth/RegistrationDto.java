package com.example.project.dto.responseDto.auth;

import com.example.project.validation.annotations.Description;
import com.example.project.validation.annotations.Password;
import com.example.project.validation.annotations.Username;
import jakarta.validation.constraints.Email;

public record RegistrationDto
        (
                @Username(allowEmpty = false)
                String username,
                @Password(allowEmpty = false)
                String password,
                @Email
                String email,
                @Username
                String first_name,
                @Username
                String last_name,
                @Description
                String description
        )
{


}
