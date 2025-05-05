package com.example.project.mappers;

import com.example.project.dto.UpdateUserDto;
import com.example.project.entity.FirstEmails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    @Mapping(source = "firstEmail", target = "email")
    FirstEmails toFirstEmails(UpdateUserDto userDto);
}
