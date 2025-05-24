package com.example.project.repo.adm;

import com.example.project.dto.responseDto.adm.AdminUserResponseDto;
import com.example.project.dto.responseDto.adm.UpdateRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdmUserRepository {

    Optional<AdminUserResponseDto> findByUsername(String username);
    Page<AdminUserResponseDto> getPage(Pageable pageable);

    void banByUsername(String username);
    void banById(Long id);
    void unBanByUsername(String username);
    void unBanById(Long id);

    void updateRole(int role_id, Long user_id);

    Optional<UpdateRole> getUpdateRole(Long id);
}
