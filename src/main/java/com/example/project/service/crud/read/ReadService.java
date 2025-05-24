package com.example.project.service.crud.read;

import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.entity.User;

public interface ReadService {
    User byUsername(String username);
    UserResponseDto read(User user);
}
