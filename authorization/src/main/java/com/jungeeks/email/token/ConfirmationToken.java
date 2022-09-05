package com.jungeeks.email.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    private Long id;
    @NotBlank
    private String token;
    @NotBlank
    private LocalDateTime createAt;
    @NotBlank
    private LocalDateTime expiredAt;
    @NotBlank
    private LocalDateTime confirmedAt;

    public ConfirmationToken(String token,
                             LocalDateTime createAt,
                             LocalDateTime expiredAt) {
        this.token = token;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
    }
}
