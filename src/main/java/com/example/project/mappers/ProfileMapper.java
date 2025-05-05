package com.example.project.mappers;

import com.example.project.dto.RegistrationUserDto;
import com.example.project.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "description",target = "description")
    Profile toProfile(RegistrationUserDto profileDto);
}
