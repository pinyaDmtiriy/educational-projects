package com.example.project.dto.responseDto.user;

import com.example.project.validation.annotations.Description;
import com.example.project.validation.annotations.Password;
import com.example.project.validation.annotations.Username;
import jakarta.validation.constraints.Email;

public record UpdateUserDto
        (
                @Username(allowEmpty = true)
                String username,
                @Password(allowEmpty = true)
                String password,
                @Email
                String email,
                @Username(allowEmpty = true)
                String first_name,
                @Username(allowEmpty = true)
                String last_name,
                @Description(allowEmpty = true)
                String description
        )
{
}
