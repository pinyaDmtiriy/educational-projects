package com.example.project.controller.admController;

import com.example.project.dto.responseDto.adm.AdminUserResponseDto;
import com.example.project.service.adm.one.AdmOneService;
import com.example.project.validation.annotations.Username;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/adm_one")
@PreAuthorize("hasRole('ADMIN_ONE')")
@Validated
@Slf4j
public class AdmOneController {

    private AdmOneService admOneService;

    public AdmOneController(AdmOneService admOneService) {
        this.admOneService = admOneService;
    }

    @GetMapping("/fullInfoUser/username/{username}")
    public ResponseEntity<AdminUserResponseDto> fullInfoUser
            (
                    @Username
                    @Valid
                    @PathVariable
                    String username
            ) {
        log.info("Обращение к endpoint /api/adm_one/fullInfoUser/username/{}", username);
        return ResponseEntity.ok(admOneService.getUser(username));
    }

    @GetMapping("/fullInfoUserPage")
    public ResponseEntity<Page<AdminUserResponseDto>> fullInfoUserPage
            (
                    @PageableDefault
                    Pageable pageable
            )
    {
        log.info("Обращение к endpoint /api/adm_one/fullInfoUserPage");
        return ResponseEntity.ok(admOneService.getPage(pageable));
    }

}
