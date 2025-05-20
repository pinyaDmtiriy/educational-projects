package com.example.project.enumName;

import org.springframework.security.core.GrantedAuthority;

public enum RoleName implements GrantedAuthority {
    ROLE_ADMIN,ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }

}
