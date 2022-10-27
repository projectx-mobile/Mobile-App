package com.jungeeks.aws.service.photoStorage.enums;

public enum PHOTO_TYPE {

    FAMILY_TASK("familyTask/"),
    REWARD("reward/"),
    USER("user/");

    private final String prefix;

    PHOTO_TYPE(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return prefix;
    }
}
