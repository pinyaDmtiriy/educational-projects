package com.example.project.service.auth;

import com.example.project.dto.responseDto.auth.AuthDto;
import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.entity.User;
import com.example.project.pojo.JwtResponse;
import com.example.project.security.util.JwtUtil;
import com.example.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserService userService;
    private AuthenticationManager authentication;
    private JwtUtil jwtUtil;

    public AuthenticationServiceImpl(UserService userService, AuthenticationManager authentication, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authentication = authentication;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public ResponseMessage registration(RegistrationDto user) {
        log.info("вызов AuthenticationService.registration");
       return userService.create(user);
    }

    @Override
    public JwtResponse login(AuthDto dto) {
        log.info("Вызов AuthenticationService.login");

        log.debug("Попытка аунтефикации");
        Authentication auth = authentication.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
        log.debug("Аунтефикация успешна");

        log.debug("Попытка достать user из security context");
        User user = (User) auth.getPrincipal();
        log.debug("user в переменной!");

        log.debug("проверка статуса");
        userService.checkStatus(user);

        log.debug("выдача токена");
        return JwtResponse.login(jwtUtil.generateToken(user), user);
    }
}
