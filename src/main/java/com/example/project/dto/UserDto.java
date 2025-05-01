package com.example.project.dto;

import java.util.Set;

public record UserDto
        (
                String username,
                Set<String> roles
        )
{
}
