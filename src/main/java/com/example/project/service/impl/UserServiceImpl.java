package com.example.project.service.impl;

import com.example.project.dto.*;
import com.example.project.entity.*;
import com.example.project.enumName.RoleName;
import com.example.project.enumName.StatusName;
import com.example.project.exception.ex.BANNED;
import com.example.project.mappers.EmailMapper;
import com.example.project.mappers.ProfileMapper;
import com.example.project.repo.UserRepository;
import com.example.project.service.UserService;
import com.example.project.service.crud.create.CreateService;
import com.example.project.service.crud.delete.DeleteService;
import com.example.project.service.crud.read.ReadService;
import com.example.project.service.crud.update.UpdateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    CreateService createService;
    DeleteService deleteService;
    ReadService readService;
    UpdateService updateService;


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
    public void updateUserByUsername(String username, UpdateUserDto userDto) {
        updateService.updateUserByUsername(username,userDto);
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
