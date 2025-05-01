package com.example.project.service.impl;

import com.example.project.dto.UpdateUserDto;
import com.example.project.dto.UserDto;
import com.example.project.entity.Password;
import com.example.project.entity.User;
import com.example.project.repo.UserRepository;
import com.example.project.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public User getByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public Page<User> getPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        repo.deleteByUsername(username);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateUserByUsername(String username, UpdateUserDto userDto) {
        User exist = repo.findByUsername(username);

        if(exist == null) {
            throw new EntityNotFoundException();
        }

        updateUsername(exist,userDto);
        updatePassword(exist,userDto);
    }

    private void updateUsername(User exist, UpdateUserDto userDto) {
        userDto.username()
                .filter(s -> !s.trim().isEmpty())
                .ifPresent(exist::setUsername);
    }

    private void updatePassword(User exist, UpdateUserDto userDto) {
        userDto.password()
                .filter(s -> !s.trim().isEmpty())
                .ifPresent(u -> exist.setPassword(new Password(u)));
    }

    @Override
    public void updateProfileByUsername(String username) {

    }



}
