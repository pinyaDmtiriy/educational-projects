package com.example.project.mappers.util;

import com.example.project.entity.FirstEmails;
import com.example.project.entity.Role;
import org.mapstruct.Named;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapperUtil {

    private PasswordEncoder passwordEncoder;

    public UserMapperUtil(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toSet());
    }
}
