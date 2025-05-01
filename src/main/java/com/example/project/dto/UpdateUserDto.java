package com.example.project.dto;

import java.util.Optional;

public record UpdateUserDto
        (
             Optional<String> username,
             Optional<String> password
        )
{
}
