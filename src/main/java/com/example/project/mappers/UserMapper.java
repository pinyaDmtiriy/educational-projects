package com.example.project.mappers;

import com.example.project.dto.RegistrationUserDto;
import com.example.project.dto.UserDto;
import com.example.project.entity.User;
import com.example.project.mappers.util.UserMapperUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapperUtil.class})
public interface UserMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "firstEmail",target = "firstEmail", qualifiedByName = "firstEmailToObjFirstEmails")
    @Mapping(source = "password", target = "password", qualifiedByName = "coderPassword")
    User toUser(RegistrationUserDto userDto);

    @Mapping(source = "username", target = "username")
    @Mapping(source = "roles", target = "roles", qualifiedByName = "setRoleToSetString")
    UserDto toDto(User user);

    default Page<UserDto> toPageDto(Page<User> page) {
        List<UserDto> userDto = page.getContent().stream()
                .map(this::toDto)
                .toList();
        return new PageImpl<>(userDto, page.getPageable(), page.getTotalElements());
    }
}
