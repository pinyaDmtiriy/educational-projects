package com.example.project.pojo;

import com.example.project.dto.responseDto.adm.UpdateRole;
import com.example.project.dto.responseDto.adm.UpdateRoleResponseDto;
import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseBuilder {

    public static UpdateRoleResponseDto updateRoleResponseDto(UpdateRole updateRole, List<String> oldRole) {

        List<String> newRole = updateRole.roles();
        String username = updateRole.username();
        String first_name = updateRole.first_name();
        String email = updateRole.email();

        return new UpdateRoleResponseDto
                (
                        oldRole,
                        newRole,
                        username,
                        first_name,
                        email
                );
    }

    public static UserResponseDto userResponseDto(User user) {

        String username = user.getUsername();
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .toList();

        String first_name = user.getProfile().getFirstName();
        String last_name = user.getProfile().getLastName();

        String email = user.getFirstEmail().getEmail();

        return new UserResponseDto
                (
                        username,
                        roles,
                        first_name,
                        last_name,
                        email
                );
    }

    public static ResponseMessage responseMessage(String message) {
        return new ResponseMessage(message);
    }

}
