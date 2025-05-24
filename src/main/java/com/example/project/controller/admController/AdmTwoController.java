package com.example.project.controller.admController;

import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.service.adm.two.AdmTwoService;
import com.example.project.validation.annotations.Username;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/adm_two")
@PreAuthorize("hasRole('ADMIN_TWO')")
@Validated
public class AdmTwoController {

    private AdmTwoService admTwoService;

    public AdmTwoController(AdmTwoService admTwoService) {
        this.admTwoService = admTwoService;
    }

    @PostMapping("/ban/username/{username}")
    public ResponseEntity<ResponseMessage> ban
            (
                    @Valid
                    @Username
                    @PathVariable
                    String username
            ) {
      return ResponseEntity.ok(admTwoService.ban(username));
    }

    @PostMapping("/ban/id/{id}")
    public ResponseEntity<ResponseMessage> ban
            (
                    @Positive
                    @PathVariable
                    Long id
            ) {
       return ResponseEntity.ok(admTwoService.ban(id));
    }

    @PostMapping("/unban/username/{username}")
    public ResponseEntity<ResponseMessage> unban
            (
                    @Username
                    @Valid
                    @PathVariable
                    String username
            ) {
          return ResponseEntity.ok(admTwoService.unban(username));
    }

    @PostMapping("/unban/id/{id}")
    public ResponseEntity<ResponseMessage> unban
            (
                    @Positive
                    @PathVariable
                    Long id
            ) {
       return ResponseEntity.ok(admTwoService.unban(id));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<ResponseMessage> updateUser
            (
                    @Valid
                    @RequestBody
                    UpdateUserDto userDto
            ) {
        return ResponseEntity.ok(admTwoService.updateUser(userDto));
    }


}


