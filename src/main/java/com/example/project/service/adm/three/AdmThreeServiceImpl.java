package com.example.project.service.adm.three;

import com.example.project.dto.responseDto.adm.UpdateRole;
import com.example.project.dto.responseDto.adm.UpdateRoleResponseDto;
import com.example.project.dto.responseDto.auth.RegistrationDto;
import com.example.project.dto.responseDto.user.ResponseMessage;
import com.example.project.dto.responseDto.user.UpdateUserDto;
import com.example.project.entity.User;
import com.example.project.enumName.RoleName;
import com.example.project.pojo.ResponseBuilder;
import com.example.project.repo.UserRepository;
import com.example.project.repo.adm.AdmUserRepository;
import com.example.project.service.crud.create.CreateService;
import com.example.project.service.crud.read.ReadService;
import com.example.project.service.crud.update.UpdateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmThreeServiceImpl implements AdmThreeService {

    private ReadService readService;
    private CreateService createService;
    private AdmUserRepository admUserRepository;
    private UpdateService updateService;
    private UserRepository userRepository;

    public AdmThreeServiceImpl(ReadService readService, CreateService createService, AdmUserRepository admUserRepository, UpdateService updateService, UserRepository userRepository) {
        this.readService = readService;
        this.createService = createService;
        this.admUserRepository = admUserRepository;
        this.updateService = updateService;
        this.userRepository = userRepository;
    }

    @Override
    public void updateUser(UpdateUserDto userDto) {
        User user = userRepository.findByUsername(userDto.username());
        updateService.updateUser(userDto, user);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public ResponseMessage createUser(RegistrationDto user) {
        createService.create(user);
        return ResponseBuilder.responseMessage("Пользователь успешно создан");
    }

    @Override
    public UpdateRoleResponseDto giveRole(String username, int role_id) {
        switch (role_id){
            case 1 -> {
                role_id = RoleName.ROLE_USER.toInt();
            }
            case 2 -> {
                role_id = RoleName.ROLE_ADMIN_ONE.toInt();
            }
            case 3 -> {
                role_id = RoleName.ROLE_ADMIN_TWO.toInt();
            }
            default -> throw new EntityNotFoundException();
        }

        User user = readService.byUsername(username);
        Long user_id = user.getId();

        List<String> oldRole = user.getRoles()
                .stream()
                .map(role -> role.getRoleName().name())
                .toList();

        admUserRepository.updateRole(role_id, user_id);

        UpdateRole updateRole = admUserRepository.getUpdateRole(user_id)
                .orElseThrow(EntityNotFoundException::new);

        return ResponseBuilder.updateRoleResponseDto(updateRole, oldRole);
    }
}
