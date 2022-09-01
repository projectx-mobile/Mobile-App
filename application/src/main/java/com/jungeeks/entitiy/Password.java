package com.jungeeks.entitiy;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sec_pass")
public class Password implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String hash;
    private String salt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password password)) return false;

        if (!Objects.equals(user, password.user)) return false;
        if (!Objects.equals(hash, password.hash)) return false;
        return Objects.equals(salt, password.salt);
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (hash != null ? hash.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        return result;
    }
}