package com.example.project.mappers;

import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.entity.FirstEmails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    @Mapping(source = "email", target = "email")
    FirstEmails toFirstEmails(UpdateUserDto userDto);
}
