package com.example.project.controller.userController;

import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Validated
@PreAuthorize("hasRole('USER')")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public ResponseEntity<UserResponseDto> read() {
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
        return ResponseEntity.ok(userService.update(user));
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity.BodyBuilder deleteUserById() {
        userService.delete();
        return ResponseEntity.status(HttpStatusCode.valueOf(204));
    }
}
