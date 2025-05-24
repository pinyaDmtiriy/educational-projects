package com.example.project.service.adm.one;

import com.example.project.dto.responseDto.adm.AdminUserResponseDto;
import com.example.project.repo.adm.AdmUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdmOneServiceImpl implements AdmOneService {

    private AdmUserRepository admUserRepository;

    public AdmOneServiceImpl(AdmUserRepository admUserRepository) {
        this.admUserRepository = admUserRepository;
    }

    @Override
    public AdminUserResponseDto getUser(String username) {
        return admUserRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<AdminUserResponseDto> getPage(Pageable pageable) {
        return admUserRepository.getPage(pageable);
    }

}
