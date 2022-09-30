package com.jungeeks.email.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Setter
@Getter
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String token;
    private LocalDateTime createAt;
    private LocalDateTime expiresAt;
    @NotBlank
    private LocalDateTime confirmedAt;
    private String email;

    public ConfirmationToken(String token,
                             LocalDateTime createAt,
                             LocalDateTime expiresAt, String email) {
        this.token = token;
        this.createAt = createAt;
        this.expiresAt = expiresAt;
        this.email = email;
    }
}
