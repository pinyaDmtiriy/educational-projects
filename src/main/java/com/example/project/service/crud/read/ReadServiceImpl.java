package com.example.project.service.crud.read;

import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.entity.User;
import com.example.project.pojo.ResponseBuilder;
import com.example.project.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReadServiceImpl implements ReadService{

    private UserRepository userRepository;


    public ReadServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User byUsername(String username) {
        log.info("Вызов ReadService.byUsername");

        log.debug("Передача данных в userRepository.findByUsername()");
        return userRepository.findByUsername(username);
    }

    @Override
    public UserResponseDto read(User user) {
        log.info("Вызов ReadService.read");

        log.debug("Передача данных в ResponseBuilder.userResponseDto()");
        return ResponseBuilder.userResponseDto(user);
    }
}
