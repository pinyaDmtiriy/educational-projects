package com.example.project.service.crud.read;

import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.entity.User;
import com.example.project.pojo.ResponseBuilder;
import com.example.project.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ReadServiceImpl implements ReadService{

    private UserRepository userRepository;


    public ReadServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User byUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserResponseDto read(User user) {
        return ResponseBuilder.userResponseDto(user);
    }
}
