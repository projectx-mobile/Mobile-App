package com.jungeeks.service;

import com.jungeeks.entity.Credentials;
import com.jungeeks.entity.SecurityUserFirebase;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {

    SecurityUserFirebase getUser();
    Credentials getCredentials();
    boolean isPublic();
    String getBearerToken(HttpServletRequest request);
}
