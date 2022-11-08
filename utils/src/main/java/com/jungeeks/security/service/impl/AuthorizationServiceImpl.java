package com.jungeeks.security.service.impl;

import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("utils-authorizationServiceImpl")
public class AuthorizationServiceImpl implements AuthorizationService {

    public SecurityUserFirebase getUser() {
        SecurityUserFirebase securityUserFirebasePrincipal = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof  SecurityUserFirebase) {
            securityUserFirebasePrincipal = ((SecurityUserFirebase) principal);
        }
        return securityUserFirebasePrincipal;
    }
}