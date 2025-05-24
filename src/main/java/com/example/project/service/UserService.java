package com.example.project.service;

import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.entity.User;

public interface UserService {
    ResponseMessage create(RegistrationDto user);
    void delete();
    ResponseMessage update(UpdateUserDto userDto);
    UserResponseDto read();

    void checkStatus(User user);
}
