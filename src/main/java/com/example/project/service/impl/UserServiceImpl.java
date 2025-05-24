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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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
        log.info("UserService.create() вызван для пользователя: {}", user.username());

        log.info("Передача данных в CreateService.create()");
        createService.create(user);
        return ResponseBuilder.responseMessage("registration successful");
    }

    @Override
    public void delete() {
        User user = getCurrentUser();
        log.info("UserService.delete() вызван для пользователя: {}", user.getUsername());

        log.info("Передача данных в checkStatus()");
        checkStatus(user);

        log.info("Передача данных в DeleteService.delete()");
        deleteService.delete(user.getId());
        log.info("Успешное удаление пользователя");
    }

    @Override
    public ResponseMessage update(UpdateUserDto userDto) {
        User user = getCurrentUser();
        log.info("UserService.update вызван для пользователя: {}", user.getUsername());

        log.info("Передача данных в checkStatus()");
        checkStatus(user);

        log.info("Передача данных в UpdateService.updateUser()");
        updateService.updateUser(userDto, user);
        log.info("Успешное обновление данных пользователя");

        return ResponseBuilder.responseMessage("data update!");
    }

    @Override
    public UserResponseDto read() {
        User user = getCurrentUser();
        log.info("UserService.read вызван для пользователя: {}", user.getUsername());

        log.info("Передача данных в checkStatus()");
        checkStatus(user);

        log.info("Передача данных в ReadService.read");
        return readService.read(user);
    }


    public void checkStatus(User user) {
        if(user.getStatus().equals(StatusName.BANNED)) {
            throw new BANNED("BANNED");
        }
        log.info("Успешная проверка в checkStatus()");
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User)) {
            throw new IllegalStateException("Current user is not properly authenticated");
        }
        return (User) auth.getPrincipal();
    }

}
