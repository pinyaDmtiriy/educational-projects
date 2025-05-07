package com.example.project.service;

import com.example.project.dto.*;
import com.example.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    void create(RegistrationUserDto user);
    void createProfile(RegistrationProfileDto profile);
    void deleteById(Long id);
    void deleteByUsername(String username);
    UserDto getById(Long id);
    UserDto getByUsername(String username);
    Page<UserDto> getPage(Pageable pageable);
    void updateUser(UpdateUserDto userDto);
    void updateProfileByUsername(String username, UpdateProfileDto updateProfileDto);

    User byUsername(String username);
    void checkStatus(User user);
}
