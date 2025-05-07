package com.example.project.service.crud.update;

import com.example.project.dto.UpdateProfileDto;
import com.example.project.dto.UpdateUserDto;
import com.example.project.entity.Profile;
import com.example.project.entity.User;
import com.example.project.mappers.EmailMapper;
import com.example.project.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateServiceImpl implements UpdateService{

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private EmailMapper emailMapper;

    public UpdateServiceImpl(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository, EmailMapper emailMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailMapper = emailMapper;
    }

    //    USER
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateUser(UpdateUserDto userDto) {
        User exist = getCurrentUserAndCheck();

        updateUsername(exist,userDto);
        updatePassword(exist,userDto);
        updateEmail(exist,userDto);
    }


    private void updateUsername(User exist, UpdateUserDto userDto) {
        if(userDto.username() != null && !userDto.username().trim().isEmpty()) {
            exist.setUsername(userDto.username());
        }
    }

    private void updatePassword(User exist, UpdateUserDto userDto) {
        if(userDto.password() != null && !userDto.password().trim().isEmpty()) {
            exist.setPassword(passwordEncoder.encode(userDto.password()));
        }
    }

    private void updateEmail(User exist, UpdateUserDto userDto) {
        if(userDto.firstEmail() != null && !userDto.firstEmail().trim().isEmpty()) {
            exist.addFirstEmail(emailMapper.toFirstEmails(userDto));
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
//    PROFILE
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateProfileByUsername(String username, UpdateProfileDto updateProfileDto) {
        User exist = getCurrentUserAndCheck();
        Profile profile = exist.getProfile();

        checkProfile(exist.getProfile());

        updateFirstName(profile, updateProfileDto.firstName());
        updateLastName(profile, updateProfileDto.lastName());
        updateDescription(profile, updateProfileDto.description());

        userRepository.save(exist);
    }

    private void updateFirstName(Profile profile,String n) {
        if(n != null && !n.trim().isEmpty()) {
            profile.setFirstName(n);
        }
    }

    private void updateLastName(Profile profile, String n) {
        if(n != null && !n.trim().isEmpty()) {
            profile.setLastName(n);
        }
    }

    private void updateDescription(Profile profile, String n) {
        if(n != null && !n.trim().isEmpty()) {
            profile.setDescription(n);
        }
    }

    private void checkProfile(Profile profile) {
        if(profile == null) {
            throw new EntityNotFoundException("profile doesn't exist");
        }
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
//    ОБЩИЕ МЕТОДЫ

    private User getCurrentUserAndCheck() {
        User exist = getCurrentUser();
        if(exist == null) {
            throw new EntityNotFoundException();
        }
        return exist;
    }


    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }
}
