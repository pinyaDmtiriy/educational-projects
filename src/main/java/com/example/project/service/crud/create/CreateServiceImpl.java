package com.example.project.service.crud.create;

import com.example.project.dto.RegistrationUserDto;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.enumName.RoleName;
import com.example.project.enumName.StatusName;
import com.example.project.mappers.ProfileMapper;
import com.example.project.mappers.UserMapper;
import com.example.project.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CreateServiceImpl implements CreateService{

    public UserRepository userRepository;
    public UserMapper userMapper;
    public ProfileMapper profileMapper;

    public CreateServiceImpl(UserRepository userRepository, UserMapper userMapper, ProfileMapper profileMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.profileMapper = profileMapper;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void create(RegistrationUserDto user) {
        User us = userMapper.toUser(user);

        us.setRoles(Set.of(new Role(RoleName.USER)));
        us.setStatus(StatusName.UNBANNED);

        userRepository.save(us);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createProfile(RegistrationUserDto profile) {
        User user = getCurrentUser();
        user.addProfile(profileMapper.toProfile(profile));

        userRepository.save(user);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
