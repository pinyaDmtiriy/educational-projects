package com.example.project.service.adm.one;

import com.example.project.dto.responseDto.adm.AdminUserResponseDto;
import com.example.project.entity.User;
import com.example.project.repo.adm.AdmUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdmOneServiceImpl implements AdmOneService {

    private AdmUserRepository admUserRepository;

    public AdmOneServiceImpl(AdmUserRepository admUserRepository) {
        this.admUserRepository = admUserRepository;
    }

    @Override
    public AdminUserResponseDto getUser(String username) {
        log.info("Вызван AdmOneService.getUser(), вызван администратором: {}", getCurrentUser());
        return admUserRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<AdminUserResponseDto> getPage(Pageable pageable) {
        log.info("Вызван AdmOneService.getPage(), вызван администратором: {}", getCurrentUser());
        return admUserRepository.getPage(pageable);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User)) {
            throw new IllegalStateException("Current user is not properly authenticated");
        }
        return (User) auth.getPrincipal();
    }

}
