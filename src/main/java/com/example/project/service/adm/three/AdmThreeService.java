package com.example.project.service.adm.three;


import com.example.project.dto.responseDto.adm.UpdateRoleResponseDto;
import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UpdateUserDto;

public interface AdmThreeService {

    void updateUser(UpdateUserDto userDto);

    void deleteByUsername(String username);
    void deleteById(Long id);

    ResponseMessage createUser(RegistrationDto user);

    UpdateRoleResponseDto giveRole(String username, int role_id);
}
