package com.example.project.controller.admController;


import com.example.project.dto.responseDto.adm.UpdateRoleResponseDto;
import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.service.adm.three.AdmThreeService;
import com.example.project.validation.annotations.Username;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/adm_three")
@PreAuthorize("hasRole('ADMIN_THREE')")
@RestController
@Validated
@Slf4j
public class AdmThreeController {

    private AdmThreeService admThreeService;

    public AdmThreeController(AdmThreeService admThreeService) {
        this.admThreeService = admThreeService;
    }

    @DeleteMapping("/delete/username/{username}")
    public ResponseEntity.BodyBuilder deleteUserByUsername
            (
                    @Valid
                    @PathVariable
                    @Username
                    String username
            ) {
        log.info("Обращение к endpoint /api/adm_three/delete/username/{}", username);
        admThreeService.deleteByUsername(username);
        return ResponseEntity.status(HttpStatusCode.valueOf(204));
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity.BodyBuilder deleteUserById
            (
                    @Positive
                    @PathVariable
                    Long id
            ) {
        log.info("Обращение к endpoint /api/adm_three/delete/id/{}", id);
        admThreeService.deleteById(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(204));
    }


    @PostMapping("/createUser")
    public ResponseEntity<ResponseMessage> createUser
            (
                    @Valid
                    @RequestBody
                    RegistrationDto userDto
            ) {
        log.info("Обращение к endpoint /api/adm_three/createUser/{}", userDto);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(admThreeService.createUser(userDto));
    }

    @PostMapping("/giveRole/{username}/{role_id}")
    public ResponseEntity<UpdateRoleResponseDto> giveRole
            (
                    @Valid
                    @Username
                    @PathVariable
                    String username,

                    @Positive
                    @PathVariable
                    int role_id
            )
    {
        log.info("Обращение к endpoint /api/adm_three/giveRole/{}/{}", username,role_id);
        return ResponseEntity.ok(admThreeService.giveRole(username, role_id));
    }

}
