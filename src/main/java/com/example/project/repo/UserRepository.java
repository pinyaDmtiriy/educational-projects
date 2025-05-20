package com.example.project.repo;

import com.example.project.dto.UserDto;
import com.example.project.entity.Profile;
import com.example.project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository {
    User findByUsername(String username);
    User findById(Long id);
    void create(User user);
    void createProfile(Profile profile, Long id);
    void update(User user, Long id);
    void updateProfile(Profile profile, Long id);
    Page<UserDto> getPage(Pageable pageable);
    void deleteByUsername(String username);
    void deleteById(Long id);
}
