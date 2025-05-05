package com.example.project.service.crud.create;

import com.example.project.dto.RegistrationUserDto;

public interface CreateService {
    void create(RegistrationUserDto user);
    void createProfile(RegistrationUserDto profile);
}
