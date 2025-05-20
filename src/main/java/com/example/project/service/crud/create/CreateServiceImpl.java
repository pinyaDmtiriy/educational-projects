package com.example.project.service.crud.create;

import com.example.project.dto.RegistrationProfileDto;
import com.example.project.dto.RegistrationUserDto;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.enumName.RoleName;
import com.example.project.enumName.StatusName;
import com.example.project.mappers.ProfileMapper;
import com.example.project.mappers.UserMapper;
import com.example.project.repo.RoleRepository;
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
    public RoleRepository roleRepository;
    public ProfileMapper profileMapper;

    public CreateServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, ProfileMapper profileMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void create(RegistrationUserDto dto) {
        User user = userMapper.toUser(dto);
        user.setStatus(StatusName.UNBANNED);

        userRepository.create(user);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createProfile(RegistrationProfileDto profile) {
        User user = getCurrentUser();
        user.addProfile(profileMapper.toProfile(profile));

        userRepository.createProfile(user.getProfile(),user.getId());
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    private Role getRole(RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
