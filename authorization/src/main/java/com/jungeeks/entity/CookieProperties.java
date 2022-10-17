package com.jungeeks.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CookieProperties {

    String domain;
    String path;
    boolean httpOnly;
    boolean secure;
    int maxAgeInMinutes;
}