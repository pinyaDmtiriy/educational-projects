package com.example.project.dto.responseDto.adm;

import java.util.List;

public record UpdateRole
        (
                List<String> roles,
                String username,
                String first_name,
                String email
        )
{
}
