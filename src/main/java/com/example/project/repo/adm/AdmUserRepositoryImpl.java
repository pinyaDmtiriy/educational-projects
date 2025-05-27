package com.example.project.repo.adm;

import com.example.project.dto.responseDto.adm.AdminUserResponseDto;
import com.example.project.dto.responseDto.adm.UpdateRole;
import com.example.project.enumName.StatusName;
import com.example.project.load.SqlLoader;
import com.example.project.repo.sqlQuery.SqlPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class AdmUserRepositoryImpl implements AdmUserRepository{

    private JdbcTemplate template;
    private SqlLoader sqlLoader;

    private String STATUS_BAN = StatusName.BANNED.name();
    private String STATUS_UNBAN = StatusName.UNBANNED.name();

    public AdmUserRepositoryImpl(JdbcTemplate template, SqlLoader sqlLoader) {
        this.template = template;
        this.sqlLoader = sqlLoader;
    }


    @Override
    public Optional<AdminUserResponseDto> findByUsername(String username) {
        log.info("Вызов AdmUserRepository.findByUsername");

        log.debug("Загрузка sql в память");
        String sql = sqlLoader.load(SqlPath.ADM_FIND_USER_RESPONSE_DTO.toPath());
        log.debug("sql загружен");

        log.debug("Вызов к бд, маппинг до AdminUserResponseDto");
        AdminUserResponseDto us = template.queryForObject(sql, new Object[]{username}, (res,__) ->
         new AdminUserResponseDto
                    (
                            res.getLong("id"),
                            res.getString("username"),
                            res.getString("status"),
                            res.getString("email"),
                            List.of(res.getString("role")),
                            res.getString("first_name"),
                            res.getString("last_name"),
                            res.getString("description")
                    )
        );
        log.debug("AdminUserResponseDto создано");

        log.debug("Возврат данных в Optional.ofNullable(AdminUserResponseDto)");
        return Optional.ofNullable(us);
    }

    @Override
    public Page<AdminUserResponseDto> getPage(Pageable pageable) {
        log.info("Вызов AdmUserRepository.getPage");

        log.debug("Загрузка sql в память");
        String sql = sqlLoader.load(SqlPath.GET_PAGE_ADM_USER_RESPONSE.toPath());
        log.debug("sql загружен");

        String countSql = "SELECT COUNT(*) FROM users"; // или другой count-запрос, если фильтрация будет

        log.debug("sql Запрос к бд");
        List<AdminUserResponseDto> users = template.query(
                sql,
                new Object[]{pageable.getPageSize(), pageable.getOffset()},
                (res, rowNum) -> new AdminUserResponseDto(
                        res.getLong("id"),
                        res.getString("username"),
                        res.getString("status"),
                        res.getString("email"),
                        List.of(res.getString("role")),
                        res.getString("first_name"),
                        res.getString("last_name"),
                        res.getString("description")
                )
        );
        log.debug("Запрос успешен");

        log.debug("Запрос в бд для подсчета строк");
        Long total = template.queryForObject(countSql, Long.class);
        log.debug("Запрос успешен");

        log.debug("Сборка Page, и возврат данных");
        return new PageImpl<>(users, pageable, total);
    }

    @Override
    public void banByUsername(String username) {
        log.info("Вызов AdmUserRepository.banByUsername");

        log.debug("Загрузка sql в память");
        String sql = sqlLoader.load(SqlPath.BAN_BY_USERNAME.toPath());
        log.debug("sql загружен");

        log.debug("Запрос в бд");
        template.update(sql, STATUS_BAN, username);
        log.debug("Запрос успешен");
    }

    @Override
    public void banById(Long id) {
        log.info("Вызов AdmUserRepository.banById");

        log.debug("Загрузка sql в память");
        String sql = sqlLoader.load(SqlPath.BAN_BY_ID.toPath());
        log.debug("sql загружен");

        log.debug("Запрос в бд");
        template.update(sql, STATUS_BAN, id);
        log.debug("Запрос успешен");

    }

    @Override
    public void unBanByUsername(String username) {
        log.info("Вызов AdmUserRepository.unBanByUsername");

        log.debug("Загрузка sql в память");
        String sql = sqlLoader.load(SqlPath.UNBAN_BY_USERNAME.toPath());
        log.debug("sql загружен");

        log.debug("Запрос в бд");
        template.update(sql, STATUS_UNBAN, username);
        log.debug("Запрос успешен");
    }

    @Override
    public void unBanById(Long id) {
        log.info("Вызов AdmUserRepository.unBanById");

        log.debug("Загрузка sql в память");
        String sql = sqlLoader.load(SqlPath.UNBAN_BY_ID.toPath());
        log.debug("sql загружен");

        log.debug("Запрос в бд");
        template.update(sql,STATUS_UNBAN, id);
        log.debug("Запрос успешен");

    }

    @Override
    public void updateRole(int role_id, Long user_id) {
        log.info("Вызов AdmUserRepository.updateRole");

        log.debug("Загрузка sql в память");
        String sql = sqlLoader.load(SqlPath.UPDATE_USER_ROLE.toPath());
        log.debug("sql загружен");

        log.debug("Запрос в бд");
        template.update(sql, role_id, user_id);
        log.debug("Запрос успешен");

    }

    @Override
    public Optional<UpdateRole> getUpdateRole(Long id) {
        log.info("Вызов AdmUserRepository.getUpdateRole");

        log.debug("Загрузка sql в память");
        String sql = sqlLoader.load(SqlPath.UPDATE_ROLE_RESPONSE_DTO.toPath());
        log.debug("sql загружен");

        log.debug("Запрос в бд");
        UpdateRole updateRole = template.queryForObject(sql, new Object[]{id}, (res, __) -> new UpdateRole
                (
                        List.of(res.getString("role")),
                        res.getString("username"),
                        res.getString(("first_name")),
                        res.getString("email")
                ));
        log.debug("Запрос успешен");

        log.debug("Возврат данных Optional.ofNullable()");
        return Optional.ofNullable(updateRole);
    }


}
