package com.example.project.pojo;

import com.example.project.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class JwtResponse {

    private String token;
    private String typeToken;
    private String username;
    private List<String> roles;
    private String time;

    public JwtResponse(String token, String typeToken, String username, List<String> roles, String time) {
        this.token = token;
        this.typeToken = typeToken;
        this.username = username;
        this.roles = roles;
        this.time = time;
    }

    public static JwtResponse login(String token, User user) {
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .toList();

        return new JwtResponse
                (
                        token,
                        "Bearer",
                        user.getUsername(),
                        roles,
                        "1 hour"
                );
    }
}
