package com.example.project.service.crud.create;

import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.entity.FirstEmails;
import com.example.project.entity.Profile;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.enumName.RoleName;
import com.example.project.enumName.StatusName;
import com.example.project.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Slf4j
public class CreateServiceImpl implements CreateService{

    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;

    public CreateServiceImpl(UserRepository userRepository,@Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void create(RegistrationDto dto) {
        log.info("Вызов CreateService.create");

        User user = new User();
        FirstEmails email = new FirstEmails();
        Profile profile = new Profile();

        log.debug("Начало  маппинга");
        Set<Role> roles = Set.of(new Role(RoleName.ROLE_USER));

        profile.setFirstName(dto.first_name());
        profile.setLastName(dto.last_name());
        profile.setDescription(dto.description());

        email.setEmail(dto.email());

        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.addFirstEmail(email);
        user.addProfile(profile);

        user.setStatus(StatusName.UNBANNED);
        user.setRoles(roles);

        log.debug("конец маппинга");

        log.debug("Передача данных в userRepository.create");
        userRepository.create(user);
    }

}
