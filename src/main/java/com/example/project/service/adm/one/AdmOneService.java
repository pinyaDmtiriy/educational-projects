package com.example.project.service.adm.one;

import com.example.project.dto.responseDto.adm.AdminUserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdmOneService {

    AdminUserResponseDto getUser(String username);
    Page<AdminUserResponseDto> getPage(Pageable pageable);
}
