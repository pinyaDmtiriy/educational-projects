package com.example.project.service.crud.create;

import com.example.project.dto.RegistrationProfileDto;
import com.example.project.dto.RegistrationUserDto;

public interface CreateService {
    void create(RegistrationUserDto user);
    void createProfile(RegistrationProfileDto profile);
}
