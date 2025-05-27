package com.example.project.service.crud.update;

import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.entity.Profile;
import com.example.project.entity.User;
import com.example.project.mappers.EmailMapper;
import com.example.project.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UpdateServiceImpl implements UpdateService{

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private EmailMapper emailMapper;

    public UpdateServiceImpl(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository, EmailMapper emailMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailMapper = emailMapper;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateUser(UpdateUserDto userDto, User current) {
        log.info("Вызов UpdateService.updateUser");

        log.debug("Вызов обновляющих методов");

        log.trace("Вызов updateUsername");
        updateUsername(current,userDto);
        log.trace("Вызов updateUsername - успешен");

        log.trace("Вызов updatePassword");
        updatePassword(current,userDto);
        log.trace("Вызов updatePassword - успешен");

        log.trace("Вызов updateEmail");
        updateEmail(current,userDto);
        log.trace("Вызов updateEmail - успешен");

        log.trace("Вызов updateFirstName");
        updateFirstName(current,userDto, current.getProfile());
        log.trace("Вызов updateFirstName - успешен");

        log.trace("Вызов updateLastName");
        updateLastName(current,userDto, current.getProfile());
        log.trace("Вызов updateLastName - успешен");

        log.trace("Вызов updateDescription");
        updateDescription(current,userDto, current.getProfile());
        log.trace("Вызов updateDescription - успешен");

        log.debug("Все обновляющие методы вызваны");

        log.debug("Передача данных в userRepository.update()");
        userRepository.update(current);
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
        if(userDto.email() != null && !userDto.email().trim().isEmpty()) {
            exist.addFirstEmail(emailMapper.toFirstEmails(userDto));
        }
    }

    public void updateFirstName(User exist, UpdateUserDto userDto, Profile profile) {
        if(userDto.first_name() != null && !userDto.first_name().trim().isEmpty()) {
            profile.setFirstName(userDto.first_name());
        }
    }

   public void updateLastName(User exist, UpdateUserDto userDto, Profile profile) {
        if(userDto.last_name() != null && !userDto.last_name().trim().isEmpty()) {
            profile.setLastName(userDto.last_name());
        }
    }

    public void updateDescription(User exist, UpdateUserDto userDto, Profile profile) {
        if(userDto.description() != null && !userDto.description().trim().isEmpty()) {
            profile.setDescription(userDto.description());
        }
    }

}
