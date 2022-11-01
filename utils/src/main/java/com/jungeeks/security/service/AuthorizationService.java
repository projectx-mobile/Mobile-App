package com.jungeeks.security.service;

import com.jungeeks.security.entity.SecurityUserFirebase;

/**
 * The interface Authorization service.
 */
public interface AuthorizationService {
    /**
     * Gets user.
     *
     * @return the user
     */
    SecurityUserFirebase getUser();
}
