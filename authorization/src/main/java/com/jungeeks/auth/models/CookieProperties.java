package com.jungeeks.auth.models;

import lombok.Data;

@Data
public class CookieProperties {
    String domain;
    String path;
    boolean httpOnly;
    boolean secure;
    int maxAgeInMinutes;
}