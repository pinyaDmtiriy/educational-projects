package com.example.project.mappers.util;

import com.example.project.dto.RegistrationUserDto;
import com.example.project.entity.FirstEmails;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Named("userMapperUtil")
public class UserMapperUtil {

    private PasswordEncoder passwordEncoder;

    @Named("firstEmailToObjFirstEmails")
    public FirstEmails firstEmailToObjFirstEmails(String email) {
        if(email == null || email.trim().isEmpty()) {
            return null;
        }
        return new FirstEmails(email);
    }

    @Named("coderPassword")
    public String coderPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("setRoleToSetString")
    public Set<String>  setRoleToSetString(Set<Role> roles) {
        if(roles.isEmpty()) {
            return null;
        }
        return roles.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());
    }
}
