package com.jungeeks.security.service.imp;

import com.jungeeks.security.entity.SecurityUserFirebase;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.jungeeks.security.service.AuthorizationService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    public SecurityUserFirebase getUser() {
        SecurityUserFirebase securityUserFirebasePrincipal = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof SecurityUserFirebase) {
            securityUserFirebasePrincipal = ((SecurityUserFirebase) principal);
        }
        return securityUserFirebasePrincipal;
    }

}