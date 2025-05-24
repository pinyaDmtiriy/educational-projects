package com.example.project.service.adm.two;

import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UpdateUserDto;

public interface AdmTwoService {

    ResponseMessage ban(String username);
    ResponseMessage ban(Long id);
    ResponseMessage unban(String username);
    ResponseMessage unban(Long id);
    ResponseMessage updateUser(UpdateUserDto userDto);
}
