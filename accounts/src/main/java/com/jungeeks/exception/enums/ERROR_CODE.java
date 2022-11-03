package com.jungeeks.exception.enums;

public enum ERROR_CODE {

    FAMILY_NOT_FOUND("Family not found"),
    INVALID_REQUEST("Invalid request"),
    NOT_ENOUGH_RIGHTS("Not enough right"),
    PATH_NOT_FOUND("Path not found"),
    USER_IS_ALREADY_EXIST("User is already exist"),
    USER_NOT_FOUND("User not found");

    private String message;

    ERROR_CODE(String message) {
        this.message = message;
    }
}
