package com.example.project.repo.sqlQuery;

import java.nio.file.Path;

public enum SqlPath {
    // CREATE

    CREATE_USER_REGISTRATIONDTO("sqlQuery/create_user_registrationDto.sql"),
    CREATE_PROFILE_REGISTRATIONPROFILEDTO("sqlQuery/create_profile_registrationProfileDto.sql"),

    // UPDATE

    UPDATE_USER("sqlQuery/update_user.sql"),
    UPDATE_PROFILE("sqlQuery/update_profile.sql"),

    // FIND

    FIND_USER_BY_USERNAME("sqlQuery/find_user_by_username.sql"),
    FIND_USER_BY_ID("sqlQuery/find_user_by_id.sql"),

    // DELETE

    DELETE_USER_BY_ID("sqlQuery/delete_user_by_id.sql"),
    DELETE_USER_BY_USERNAME("sqlQuery/delete_user_by_username.sql"),

    // PAGE

    GET_PAGE_USERDTO("sqlQuery/get_page_userDto.sql");

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
