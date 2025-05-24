package com.example.project.service.crud.update;

import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.entity.User;

public interface UpdateService {
    void updateUser(UpdateUserDto userDto, User current);
}
