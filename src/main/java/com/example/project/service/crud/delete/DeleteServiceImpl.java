package com.example.project.service.crud.delete;

import com.example.project.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteServiceImpl implements DeleteService{

    private UserRepository userRepository;

    public DeleteServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
