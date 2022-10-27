package com.jungeeks.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.annotation.AccessType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUserFirebase {

    private String uid;
    private String name;
    private String email;
    private boolean isEmailVerified;
    private String issuer;
    private String picture;
}