package com.example.project.service.crud.read;

import com.example.project.dto.UserDto;
import com.example.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadService {
    UserDto getById(Long id);
    UserDto getByUsername(String username);
    User ByUsername(String username);
    Page<UserDto> getPage(Pageable pageable);
}
