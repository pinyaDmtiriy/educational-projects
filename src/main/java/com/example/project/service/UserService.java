package com.example.project.service;

import com.example.project.dto.UpdateUserDto;
import com.example.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User getById(Long id);
    User getByUsername(String username);
    Page<User> getPage(Pageable pageable);
    void updateUserByUsername(String username, UpdateUserDto userDto);
    void updateProfileByUsername(String username);
    void deleteById(Long id);
    void deleteByUsername(String username);
}
