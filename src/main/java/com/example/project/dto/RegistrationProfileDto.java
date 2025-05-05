package com.example.project.dto;

import com.example.project.validation.annotations.Description;
import com.example.project.validation.annotations.Username;

public record RegistrationProfileDto
        (
                @Username
                String firstName,
                @Username
                String lastName,
                @Description
                String description
        )
{
}
