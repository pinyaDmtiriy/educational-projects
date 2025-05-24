package com.example.project.repo.impl;

import com.example.project.dto.responseDto.user.UserResponseDto;
import com.example.project.entity.FirstEmails;
import com.example.project.entity.Profile;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.enumName.RoleName;
import com.example.project.enumName.StatusName;
import com.example.project.load.SqlLoader;
import com.example.project.repo.UserRepository;
import com.example.project.repo.sqlQuery.SqlPath;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.util.List;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private SqlLoader sqlLoader;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, SqlLoader sqlLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqlLoader = sqlLoader;
    }

    @Override
    public User findByUsername(String username) {
        try {
            String sql = sqlLoader.load(SqlPath.FIND_USER_BY_USERNAME.toPath());
            return jdbcTemplate.queryForObject(sql, new Object[]{username}, (res, __) -> new User
                    (
                            res.getLong("id"),
                            res.getString("username"),
                            res.getString("password"),
                            new FirstEmails(res.getString("email")),
                            StatusName.valueOf(res.getString("status")),
                            Set.of(new Role(RoleName.valueOf(res.getString("role")))),
                            new Profile(res.getString("first_name"),res.getString("last_name"),res.getString("description"))
                    ));
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User with username: " + username + " not found");
        }
    }

    @Override
    public User findById(Long id) {
        try {
            String sql = sqlLoader.load(SqlPath.FIND_USER_BY_ID.toPath());
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (res, __) -> new User
                    (
                            res.getLong("id"),
                            res.getString("username"),
                            res.getString("password"),
                            new FirstEmails(res.getString("email")),
                            StatusName.valueOf(res.getString("status")),
                            Set.of(new Role(RoleName.valueOf(res.getString("role"))))
                    ));
        }catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("User with id: " + id + " not found");
        }
    }

    @Override
    public void create(User user) {
        String sql = sqlLoader.load(SqlPath.CREATE_USER_REGISTRATION_DTO.toPath());

        String password = user.getPassword();
        String status = user.getStatus().name();
        String username = user.getUsername();

        String role = user.getRoles()
                .stream()
                .map((r) -> r.getRoleName().toInt())
                .toString();

        int role_id = 1;

        String description = user.getProfile().getDescription();
        String first_name = user.getProfile().getFirstName();
        String last_name = user.getProfile().getLastName();

        String email = user.getFirstEmail().getEmail();

        jdbcTemplate.queryForObject(
                sql,
                new Object[]{
                        password,
                        status,
                        username,
                        role_id,
                        description,
                        first_name,
                        last_name,
                        email},
        Long.class);
    }


    @Override
    public void update(User user) {
        String sql = sqlLoader.load(SqlPath.UPDATE_USER.toPath());

        String username = user.getUsername();
        String password = user.getPassword();
        Long id = user.getId();

        String email = user.getFirstEmail().getEmail();

        String first_name = user.getProfile().getFirstName();
        String last_name = user.getProfile().getLastName();
        String description = user.getProfile().getDescription();

        jdbcTemplate.queryForObject(
                sql, new Object[]
                        {
                                username,
                                password,
                                id,
                                email,
                                first_name,
                                last_name,
                                description
                        },
                Long.class
                );
    }


    @Override
    public void deleteByUsername(String username) {
        String sql = sqlLoader.load(SqlPath.DELETE_USER_BY_USERNAME.toPath());
        jdbcTemplate.update(sql, username);
    }

    @Override
    public void deleteById(Long id) {
        String sql = sqlLoader.load(SqlPath.DELETE_USER_BY_ID.toPath());
        jdbcTemplate.queryForObject(sql, new Object[]{id}, Long.class);
    }

    @Override
    public UserResponseDto get(Long id) {
        String sql = sqlLoader.load(SqlPath.GET_USER_RESPONSE.toPath());
        return jdbcTemplate.queryForObject(sql, new Object[]{id},(res, __) -> new UserResponseDto
                (
                        res.getString("username"),
                        List.of(res.getString("role")),
                        res.getString("first_name"),
                        res.getString("last_name"),
                        res.getString("email")
                ));
    }

}
