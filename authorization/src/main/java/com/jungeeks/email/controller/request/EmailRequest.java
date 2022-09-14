package com.jungeeks.email.controller.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@Setter
@Getter
public class EmailRequest {
    @Email
    @NotBlank
    private String email;
}
