package com.jungeeks.registration.requests;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
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
}
