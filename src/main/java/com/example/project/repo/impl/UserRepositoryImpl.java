package com.example.project.repo.impl;

import com.example.project.dto.UserDto;
import com.example.project.entity.FirstEmails;
import com.example.project.entity.Profile;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.enumName.RoleName;
import com.example.project.enumName.StatusName;
import com.example.project.load.SqlLoader;
import com.example.project.mappers.UserMapper;
import com.example.project.repo.UserRepository;
import com.example.project.repo.sqlQuery.SqlPath;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private SqlLoader sqlLoader;
    private UserMapper userMapper;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, SqlLoader sqlLoader, UserMapper userMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.sqlLoader = sqlLoader;
        this.userMapper = userMapper;
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
                            Set.of(new Role(RoleName.valueOf(res.getString("role"))))
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
        String sql = sqlLoader.load(SqlPath.CREATE_USER_REGISTRATIONDTO.toPath());

        jdbcTemplate.queryForObject(
                sql,
                new Object[]{
                        user.getPassword(),
                        user.getStatus().name(),
                        user.getUsername(),
                        user.getFirstEmail().getEmail()},
        Long.class);
    }

    @Override
    public void createProfile(Profile profile, Long id) {
        String sql = sqlLoader.load(SqlPath.CREATE_PROFILE_REGISTRATIONPROFILEDTO.toPath());

        jdbcTemplate.queryForObject(
                sql,
                new Object[]
                        {
                                profile.getDescription(),
                                profile.getFirstName(),
                                profile.getLastName(),
                                id
                        },
                Long.class);
    }

    @Override
    public void update(User user, Long id) {
        String sql = sqlLoader.load(SqlPath.UPDATE_USER.toPath());

        jdbcTemplate.queryForObject(
                sql, new Object[]
                        {
                                user.getUsername(),
                                user.getPassword(),
                                id,
                                user.getFirstEmail().getEmail()
                        },
                Long.class
                );
    }

    @Override
    public void updateProfile(Profile profile, Long id) {
        String sql = sqlLoader.load(SqlPath.UPDATE_PROFILE.toPath());

        jdbcTemplate.queryForObject(
                sql,
                new Object[]{profile.getDescription(), profile.getFirstName(), profile.getLastName(), id},
                Long.class);
    }

    @Override
    public Page<UserDto> getPage(Pageable pageable) {
        String sql = sqlLoader.load(SqlPath.GET_PAGE_USERDTO.toPath());
        String countSql = "SELECT COUNT(*) FROM users"; // или другой count-запрос, если фильтрация будет

        List<UserDto> users = jdbcTemplate.query(
                sql,
                new Object[]{pageable.getPageSize(), pageable.getOffset()},
                (res, rowNum) -> new UserDto(
                        res.getString("username"),
                        Set.of(res.getString("role"))
                )
        );

        Long total = jdbcTemplate.queryForObject(countSql, Long.class);

        return new PageImpl<>(users, pageable, total);
    }


    @Override
    public void deleteByUsername(String username) {
        String sql = sqlLoader.load(SqlPath.DELETE_USER_BY_USERNAME.toPath());
        jdbcTemplate.update(sql, username);
    }
    @Override
    public void deleteById(Long id) {
        String sql = sqlLoader.load(SqlPath.DELETE_USER_BY_ID.toPath());
        jdbcTemplate.update(sql, id);
    }

}
