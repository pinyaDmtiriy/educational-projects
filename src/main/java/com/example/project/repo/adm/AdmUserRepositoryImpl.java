package com.example.project.repo.adm;

import com.example.project.dto.responseDto.adm.AdminUserResponseDto;
import com.example.project.dto.responseDto.adm.UpdateRole;
import com.example.project.enumName.StatusName;
import com.example.project.load.SqlLoader;
import com.example.project.repo.sqlQuery.SqlPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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
        String sql = sqlLoader.load(SqlPath.ADM_FIND_USER_RESPONSE_DTO.toPath());
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

        return Optional.ofNullable(us);
    }

    @Override
    public Page<AdminUserResponseDto> getPage(Pageable pageable) {
        String sql = sqlLoader.load(SqlPath.GET_PAGE_ADM_USER_RESPONSE.toPath());
        String countSql = "SELECT COUNT(*) FROM users"; // или другой count-запрос, если фильтрация будет

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

        Long total = template.queryForObject(countSql, Long.class);

        return new PageImpl<>(users, pageable, total);
    }

    @Override
    public void banByUsername(String username) {
        String sql = sqlLoader.load(SqlPath.BAN_BY_USERNAME.toPath());

        template.update(sql, STATUS_BAN, username);
    }

    @Override
    public void banById(Long id) {
        String sql = sqlLoader.load(SqlPath.BAN_BY_ID.toPath());

        template.update(sql, STATUS_BAN, id);
    }

    @Override
    public void unBanByUsername(String username) {
        String sql = sqlLoader.load(SqlPath.UNBAN_BY_USERNAME.toPath());

        template.update(sql, STATUS_UNBAN, username);
    }

    @Override
    public void unBanById(Long id) {
        String sql = sqlLoader.load(SqlPath.UNBAN_BY_ID.toPath());

        template.update(sql,STATUS_UNBAN, id);
    }

    @Override
    public void updateRole(int role_id, Long user_id) {
        String sql = sqlLoader.load(SqlPath.UPDATE_USER_ROLE.toPath());
        template.update(sql, role_id, user_id);
    }

    @Override
    public Optional<UpdateRole> getUpdateRole(Long id) {
        String sql = sqlLoader.load(SqlPath.UPDATE_ROLE_RESPONSE_DTO.toPath());

        UpdateRole updateRole = template.queryForObject(sql, new Object[]{id}, (res, __) -> new UpdateRole
                (
                        List.of(res.getString("role")),
                        res.getString("username"),
                        res.getString(("first_name")),
                        res.getString("email")
                ));

        return Optional.ofNullable(updateRole);
    }


}
