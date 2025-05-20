package com.example.project.service.crud.read;

import com.example.project.dto.UserDto;
import com.example.project.entity.User;
import com.example.project.mappers.UserMapper;
import com.example.project.repo.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReadServiceImpl implements ReadService{

    private UserRepository userRepository;
    private UserMapper userMapper;

    public ReadServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id));
    }

    @Override
    public UserDto getByUsername(String username) {
        return userMapper.toDto(userRepository.findByUsername(username));
    }

    @Override
    public User ByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<UserDto> getPage(Pageable pageable) {
        return userRepository.getPage(pageable);
    }
}
