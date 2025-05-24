package com.example.project.security.service;

import com.example.project.entity.User;
import com.example.project.service.crud.read.ReadService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private ReadService readService;

    public CustomUserDetailsService(ReadService readService) {
        this.readService = readService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return byUsername(username);
    }


    // do not touch this method, it is needed to load the user into the Security context
    private User byUsername(String username) {
        return readService.byUsername(username);
    }
    // ***************************************************************************************

}
