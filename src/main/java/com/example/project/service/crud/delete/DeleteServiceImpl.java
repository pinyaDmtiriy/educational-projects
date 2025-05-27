package com.example.project.service.crud.delete;

import com.example.project.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DeleteServiceImpl implements DeleteService{

    private UserRepository userRepository;

    public DeleteServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(String username) {
        log.info("Вызов DeleteService.delete(String username)");

        log.debug("Передача данных в userRepository.deleteByUsername()");
        userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void delete(Long id) {
        log.info("Вызов DeleteService.delete(Long id)");

        log.debug("Передача данных в userRepository.deleteById()");
        userRepository.deleteById(id);
    }

}
