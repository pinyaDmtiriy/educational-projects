package com.example.project.controller.userController;

import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Validated
@PreAuthorize("hasRole('USER')")
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> read() {
        log.info("Обращение к endpoint /api/user/info");
      return ResponseEntity.ok(userService.read());
    }

    @PutMapping("/updateUser")
    public ResponseEntity<ResponseMessage> updateUser
            (
                    @Valid
                    @RequestBody
                    UpdateUserDto user
            )
    {
        log.info("Обращение к endpoint /api/user/updateUser");
        return ResponseEntity.ok(userService.update(user));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity.BodyBuilder deleteUserById() {
        log.info("Обращение к endpoint /api/user/deleteUser");
        userService.delete();
        return ResponseEntity.status(HttpStatusCode.valueOf(204));
    }
}
