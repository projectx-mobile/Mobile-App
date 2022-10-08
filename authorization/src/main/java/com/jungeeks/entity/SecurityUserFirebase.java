package com.jungeeks.entity;

import lombok.Data;

@Data
public class SecurityUserFirebase {

    private String uid;
    private String name;
    private String email;
    private boolean isEmailVerified;
    private String issuer;
    private String picture;

}