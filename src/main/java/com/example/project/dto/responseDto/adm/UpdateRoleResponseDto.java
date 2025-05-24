package com.example.project.dto.responseDto.adm;

import java.util.List;

public record UpdateRoleResponseDto
        (
                List<String> oldRoles,
                List<String> newRoles,
                String username,
                String first_name,
                String email
        )
{
}
