package com.example.project.dto.responseDto.adm;

import java.util.List;

public record AdminUserResponseDto
        (
              Long id,
              String username,
              String accountStatus,
              String email,
              List<String> roles,
              String firstName,
              String lastName,
              String description
        )
{
}
