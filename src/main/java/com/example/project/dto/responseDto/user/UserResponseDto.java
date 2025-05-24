package com.example.project.dto.responseDto.user;

import java.util.List;

public record UserResponseDto
        (
                String username,
                List<String> roles,
                String first_name,
                String last_name,
                String email
        )
{
}
