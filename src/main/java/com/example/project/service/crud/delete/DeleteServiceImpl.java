package com.example.project.service.crud.delete;

import com.example.project.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteServiceImpl implements DeleteService{

    private UserRepository userRepository;

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
