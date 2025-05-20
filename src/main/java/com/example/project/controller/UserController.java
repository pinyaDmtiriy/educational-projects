package com.example.project.controller;

import com.example.project.dto.UpdateUserDto;
import com.example.project.dto.UserDto;
import org.springframework.data.domain.Page;
import com.example.project.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Page<UserDto>> getUsers
            (
                    @PageableDefault
                            (
                                    size = 10,
                                    page = 0
                            )
                    Pageable pageable
            ) {
        return ResponseEntity.ok(userService.getPage(pageable));
    }

    @GetMapping("/get-byUsername/{username}")
    public ResponseEntity<UserDto> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @GetMapping("/get-ById/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/updateUser")
    public ResponseEntity<Void> updateUser
            (
                    @RequestBody UpdateUserDto user
            )
    {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteUser-ByUsername/{username}")
    public ResponseEntity.BodyBuilder deleteUserByUsername(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.status(HttpStatusCode.valueOf(204));
    }

    @DeleteMapping("/deleteUser-ById/{id}")
    public ResponseEntity.BodyBuilder deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(204));
    }
}
