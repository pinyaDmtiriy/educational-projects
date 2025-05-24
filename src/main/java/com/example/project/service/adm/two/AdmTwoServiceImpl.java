package com.example.project.service.adm.two;

import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.pojo.ResponseBuilder;
import com.example.project.repo.UserRepository;
import com.example.project.repo.adm.AdmUserRepository;
import com.example.project.service.crud.update.UpdateService;
import org.springframework.stereotype.Service;

@Service
public class AdmTwoServiceImpl implements AdmTwoService {

    private AdmUserRepository admUserRepository;
    private UserRepository userRepository;
    private UpdateService updateService;

    public AdmTwoServiceImpl(AdmUserRepository admUserRepository, UserRepository userRepository, UpdateService updateService) {
        this.admUserRepository = admUserRepository;
        this.userRepository = userRepository;
        this.updateService = updateService;
    }

    @Override
    public ResponseMessage ban(String username) {
        admUserRepository.banByUsername(username);
        return ResponseBuilder.responseMessage("пользователь с username: " + username + " забанен");
    }

    @Override
    public ResponseMessage ban(Long id) {
        admUserRepository.banById(id);
        return ResponseBuilder.responseMessage("пользователь с id: " + id + " забанен");
    }

    @Override
    public ResponseMessage unban(String username) {
        admUserRepository.unBanByUsername(username);
        return ResponseBuilder.responseMessage("пользователь с username: " + username + " разбанен");
    }

    @Override
    public ResponseMessage unban(Long id) {
        admUserRepository.unBanById(id);
        return ResponseBuilder.responseMessage("пользователь с id: " + id + " разбанен");
    }

    @Override
    public ResponseMessage updateUser(UpdateUserDto userDto) {

        updateService.updateUser(userDto, userRepository.findByUsername(userDto.username()));

        return ResponseBuilder.responseMessage("Данные пользователя c username: " + userDto.username() + " обновлены");
    }
}
