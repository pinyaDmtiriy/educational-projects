package com.example.project.repo.sqlQuery;

import java.nio.file.Path;

public enum SqlPath {

    // USER

    GET_USER_RESPONSE("sqlQuery/get_user_response.sql"),


    // CREATE

    CREATE_USER_REGISTRATION_DTO("sqlQuery/user_registrationDto.sql"),

    // UPDATE

    UPDATE_USER("sqlQuery/update_user.sql"),

    // FIND

    FIND_USER_BY_USERNAME("sqlQuery/find_user_by_username.sql"),
    FIND_USER_BY_ID("sqlQuery/find_user_by_id.sql"),

    // DELETE

    DELETE_USER_BY_ID("sqlQuery/delete_user_by_id.sql"),
    DELETE_USER_BY_USERNAME("sqlQuery/deete_user_by_username.sql"),

    // PAGE

    GET_PAGE_ADM_USER_RESPONSE("sqlQuery/adm/get_page_adm_user_response.sql"),

    // ADM
    UPDATE_USER_ROLE("sqlQuery/adm/update_user_role.sql"),
    UPDATE_ROLE_RESPONSE_DTO("sqlQuery/adm/adm_update_role_responseDto.sql"),

    BAN_BY_USERNAME("sqlQuery/adm/ban_by_username.sql"),
    BAN_BY_ID("sqlQuery/adm/ban_by_id.sql"),
    UNBAN_BY_USERNAME("sqlQuery/adm/unban_by_username.sql"),
    UNBAN_BY_ID("sqlQuery/adm/unban_by_id.sql"),

    ADM_FIND_USER_RESPONSE_DTO("sqlQuery/adm/adm_user_responseDto.sql");




    private String resourcePath;

    SqlPath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Path toPath() {
        try {
            return Path.of(ClassLoader.getSystemResource(resourcePath).toURI());
        } catch (Exception e) {
            throw new RuntimeException("Failed to resolve path: " + resourcePath, e);
        }
    }
}
