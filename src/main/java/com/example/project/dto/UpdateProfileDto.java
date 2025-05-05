package com.example.project.dto;

import com.example.project.validation.annotations.Description;
import com.example.project.validation.annotations.Username;

public record UpdateProfileDto
        (
                @Username(allowEmpty = true)
                String firstName,
                @Username(allowEmpty = true)
                String lastName,
                @Description(allowEmpty = true)
                String description
        )
{


}
