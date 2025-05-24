package com.example.project.enumName;

import org.springframework.security.core.GrantedAuthority;

public enum RoleName implements GrantedAuthority {
    ROLE_ADMIN_THREE(4),ROLE_ADMIN_TWO(3),
    ROLE_ADMIN_ONE(2),ROLE_USER(1);

    private int role_id;

    RoleName(int role_id) {
        this.role_id = role_id;
    }

    public int toInt() {
        return role_id;
    }

    @Override
    public String getAuthority() {
        return name();
    }

}
