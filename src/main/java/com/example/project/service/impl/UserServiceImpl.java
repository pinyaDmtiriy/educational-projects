package com.example.project.service.impl;

import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.entity.User;
import com.example.project.enumName.StatusName;
import com.example.project.exception.ex.BANNED;
import com.example.project.pojo.ResponseBuilder;
import com.example.project.service.UserService;
import com.example.project.service.crud.create.CreateService;
import com.example.project.service.crud.delete.DeleteService;
import com.example.project.service.crud.read.ReadService;
import com.example.project.service.crud.update.UpdateService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseMessage create(RegistrationDto user) {
        createService.create(user);
        return ResponseBuilder.responseMessage("registration succesful");
    }

    @Override
    public void delete() {
        User user = getCurrentUser();
        checkStatus(user);

        deleteService.delete(user.getId());
    }

    @Override
    public ResponseMessage update(UpdateUserDto userDto) {
        User user = getCurrentUser();
        checkStatus(user);
        updateService.updateUser(userDto, user);

        return ResponseBuilder.responseMessage("data update!");
    }

    @Override
    public UserResponseDto read() {
        User user = getCurrentUser();
        checkStatus(user);

        return readService.read(user);
    }


    public void checkStatus(User user) {
        if(user.getStatus().equals(StatusName.BANNED)) {
            throw new BANNED("BANNED");
        }
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User)) {
            throw new IllegalStateException("Current user is not properly authenticated");
        }
        return (User) auth.getPrincipal();
    }

}
