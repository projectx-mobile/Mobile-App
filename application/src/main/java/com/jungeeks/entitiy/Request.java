package com.jungeeks.entitiy;

import com.jungeeks.entitiy.enums.REQUEST_STATUS;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
public class Request implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private REQUEST_STATUS requestStatus;

    @Id
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Reward reward;
    @ManyToOne
    @JoinColumn
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request request)) return false;

        if (requestStatus != request.requestStatus) return false;
        if (!Objects.equals(reward, request.reward)) return false;
        return Objects.equals(user, request.user);
    }

    @Override
    public int hashCode() {
        int result = requestStatus != null ? requestStatus.hashCode() : 0;
        result = 31 * result + (reward != null ? reward.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}