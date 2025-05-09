package com.example.project.security.provider;

import com.example.project.service.redis.LoginAttemptServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private DaoAuthenticationProvider dao;
    private LoginAttemptServiceImpl redisService;

    public CustomAuthenticationProvider(DaoAuthenticationProvider dao, LoginAttemptServiceImpl redisService) {
        this.dao = dao;
        this.redisService = redisService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String ip = getIp();
        redisService.checkNumberOfLoginAttempts(ip);

        return dao.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return dao.supports(authentication);
    }

    private String getIp() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            return request.getRemoteAddr();
        }
        return "unknown";
    }


}
