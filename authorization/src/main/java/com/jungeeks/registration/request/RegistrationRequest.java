package com.jungeeks.registration.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Builder
@NoArgsConstructor
public class RegistrationRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
