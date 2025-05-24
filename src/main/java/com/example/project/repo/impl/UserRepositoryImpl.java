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
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private SqlLoader sqlLoader;

    private int ROLE_USER = RoleName.ROLE_USER.toInt();
    private String STATUS_UNBANNED = StatusName.UNBANNED.name();

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, SqlLoader sqlLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqlLoader = sqlLoader;
    }

    @Override
    public User findByUsername(String username) {
        log.debug("Вызван UserRepository.findByUsername()");

        try {

            log.debug("загрузка sql");
            String sql = sqlLoader.load(SqlPath.FIND_USER_BY_USERNAME.toPath());
            log.debug("sql загружен успешно!");

            log.info("Достаем из бд User");
            User user = jdbcTemplate.queryForObject(sql, new Object[]{username}, (res, __) -> new User
                    (
                            res.getLong("id"),
                            res.getString("username"),
                            res.getString("password"),
                            new FirstEmails(res.getString("email")),
                            StatusName.valueOf(res.getString("status")),
                            Set.of(new Role(RoleName.valueOf(res.getString("role")))),
                            new Profile(res.getString("first_name"),res.getString("last_name"),res.getString("description"))
                    ));
            log.info("Возвращаем user - аунтефицирован");
            return user;
        }catch (EmptyResultDataAccessException e) {
            log.error("Произошла ошибка при попытке загрузить пользователя из бд");
            throw new EntityNotFoundException("User with username: " + username + " not found");
        }
    }

    @Override
    public User findById(Long id) {
        log.debug("Вызван UserRepository.findById()");

        try {
            log.debug("Загрузка sql");
            String sql = sqlLoader.load(SqlPath.FIND_USER_BY_ID.toPath());
            log.debug("sql загружен успешно");

            log.info("Достаем из бд User");
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, (res, __) -> new User
                    (
                            res.getLong("id"),
                            res.getString("username"),
                            res.getString("password"),
                            new FirstEmails(res.getString("email")),
                            StatusName.valueOf(res.getString("status")),
                            Set.of(new Role(RoleName.valueOf(res.getString("role"))))
                    ));

            log.info("Возвращаем user");
            return user;
        }catch (EmptyResultDataAccessException e) {
            log.error("Произошла ошибка");
            throw new EntityNotFoundException("User with id: " + id + " not found");
        }
    }

    @Override
    public void create(User user) {
        log.debug("Вызван UserRepository.create()");

        log.debug("Начало загрузки sql");
        String sql = sqlLoader.load(SqlPath.CREATE_USER_REGISTRATION_DTO.toPath());
        log.debug("sql успешно загружен в память");

        log.debug("вызываем getter, кладем данные в переменной ");
        String password = user.getPassword();
        String username = user.getUsername();

        String description = user.getProfile().getDescription();
        String first_name = user.getProfile().getFirstName();
        String last_name = user.getProfile().getLastName();

        String email = user.getFirstEmail().getEmail();
        log.debug("данные успешно положены в переменные");

        try {
            log.info("Отправляем sql в бд, для создания пользователя");
            jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{
                            password,
                            STATUS_UNBANNED,
                            username,
                            ROLE_USER,
                            description,
                            first_name,
                            last_name,
                            email},
                    Long.class);
            log.info("Пользователь успешно создан!");
        }catch (DataAccessException e) {
           log.error("При создании пользователя произошла ошибка");
           throw e;
        }
    }


    @Override
    public void update(User user) {
        log.debug("Вызван UserRepository.update()");

        log.debug("Начало загрузки sql");
        String sql = sqlLoader.load(SqlPath.UPDATE_USER.toPath());
        log.debug("sql успешно загружен в память");

        log.debug("Получаем значения в переменные");
        String username = user.getUsername();
        String password = user.getPassword();
        Long id = user.getId();

        String email = user.getFirstEmail().getEmail();

        String first_name = user.getProfile().getFirstName();
        String last_name = user.getProfile().getLastName();
        String description = user.getProfile().getDescription();
        log.debug("Переменные созданы");
        try {
            log.info("Запрос на обновления пользователя в бд");
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
            log.info("Пользователь обновлен");
        }catch (DataAccessException e) {
            log.error("Произошла ошибка");
            throw e;
        }
    }


    @Override
    public void deleteByUsername(String username) {
        log.debug("Вызов UserRepository.deleteByUsername()");

        log.debug("Попытка загрузки sql в память");
        String sql = sqlLoader.load(SqlPath.DELETE_USER_BY_USERNAME.toPath());
        log.debug("sql загружен");

        try {
            log.info("запрос на удаление пользователя отправлен");
            jdbcTemplate.update(sql, username);
            log.info("Пользователь удален");
        }catch (DataAccessException e) {
            log.error("Произошла ошибка");
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Вызов UserRepository.deleteById()");

        log.debug("Попытка загрузки sql в память");
        String sql = sqlLoader.load(SqlPath.DELETE_USER_BY_ID.toPath());
        log.debug("sql загружен");

        try {
            log.info("запрос на удаление пользователя отправлен");
            jdbcTemplate.queryForObject(sql, new Object[]{id}, Long.class);
            log.info("Пользователь удален");

        }catch (DataAccessException e) {
            log.error("Произошла ошибка");
            throw e;
        }
    }

    @Override
    @Deprecated(since = "Вроде как устарел")
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
