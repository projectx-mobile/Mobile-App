package com.jungeeks.exception.enums;

public enum ERROR_CODE {

    FAMILY_NOT_FOUND("Family not found"),
    INVALID_REQUEST("Invalid request"),
    NOT_ENOUGH_RIGHTS("Not enough right"),
    PATH_NOT_FOUND("Path not found"),
    USER_IS_ALREADY_EXIST("User is already exist"),
    USER_NOT_FOUND("User not found"),
    USER_WITH_FAMILY_ID_AND_ROLE ("User with familyId and role not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    TASK_NOT_FOUND ("Task not found"),
    FAMILY_ID_IS_NOT_EQUAL ("Family id is not equal");

    private String message;

    ERROR_CODE(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
