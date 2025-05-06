package com.example.project.service.crud.update;

import com.example.project.dto.UpdateProfileDto;
import com.example.project.dto.UpdateUserDto;

public interface UpdateService {
    void updateUser(UpdateUserDto userDto);
    void updateProfileByUsername(String username, UpdateProfileDto updateProfileDto);
}
