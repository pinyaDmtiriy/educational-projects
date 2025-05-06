package com.example.project.service.impl;

import com.example.project.dto.RegistrationUserDto;
import com.example.project.dto.UpdateProfileDto;
import com.example.project.dto.UpdateUserDto;
import com.example.project.dto.UserDto;
import com.example.project.entity.User;
import com.example.project.enumName.StatusName;
import com.example.project.exception.ex.BANNED;
import com.example.project.service.UserService;
import com.example.project.service.crud.create.CreateService;
import com.example.project.service.crud.delete.DeleteService;
import com.example.project.service.crud.read.ReadService;
import com.example.project.service.crud.update.UpdateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private CreateService createService;
    private DeleteService deleteService;
    private ReadService readService;
    private UpdateService updateService;

    public UserServiceImpl(CreateService createService, DeleteService deleteService, ReadService readService, UpdateService updateService) {
        this.createService = createService;
        this.deleteService = deleteService;
        this.readService = readService;
        this.updateService = updateService;
    }

    @Override
    public void create(RegistrationUserDto user) {
        createService.create(user);
    }

    @Override
    public void createProfile(RegistrationUserDto profile) {
        createService.createProfile(profile);
    }

    @Override
    public void deleteById(Long id) {
        deleteService.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        deleteService.deleteByUsername(username);
    }

    @Override
    public UserDto getById(Long id) {
        return readService.getById(id);
    }

    @Override
    public UserDto getByUsername(String username) {
        return readService.getByUsername(username);
    }

    @Override
    public Page<UserDto> getPage(Pageable pageable) {
        return readService.getPage(pageable);
    }

    @Override
    public void updateUser(UpdateUserDto userDto) {
        updateService.updateUser(userDto);
    }

    @Override
    public void updateProfileByUsername(String username, UpdateProfileDto updateProfileDto) {
        updateService.updateProfileByUsername(username,updateProfileDto);
    }

    @Override
    public User byUsername(String username) {
        return readService.ByUsername(username);
    }

    @Override
    public void checkStatus(User user) {
        if(user.getStatus().equals(StatusName.BANNED)) {
            throw new BANNED("BANNED");
        }
    }
}
