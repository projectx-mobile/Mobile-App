package com.jungeeks.entitiy;

import com.jungeeks.entitiy.enums.REQUEST_STATUS;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Request {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn
    private Reward reward;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private REQUEST_STATUS requestStatus;
}