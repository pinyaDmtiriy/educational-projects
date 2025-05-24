package com.example.project.repo;

import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.entity.User;

public interface UserRepository {

    User findByUsername(String username);
    User findById(Long id);

    void create(User user);

    void update(User user);

    void deleteByUsername(String username);
    void deleteById(Long id);

    UserResponseDto get(Long id);
}
