package com.example.project.service.crud.create;

import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.entity.FirstEmails;
import com.example.project.entity.Profile;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.enumName.RoleName;
import com.example.project.enumName.StatusName;
import com.example.project.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CreateServiceImpl implements CreateService{

    public UserRepository userRepository;
    public PasswordEncoder passwordEncoder;

    public CreateServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void create(RegistrationDto dto) {
        User user = new User();
        FirstEmails email = new FirstEmails();
        Profile profile = new Profile();

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

        userRepository.create(user);
    }

}
