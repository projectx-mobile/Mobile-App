package com.jungeeks.registration.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    @NotBlank
    private  String name;
    @NotBlank
    @Email
    private  String email;
    @NotBlank
    private  String password;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
