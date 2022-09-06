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

    @Id
    @OneToOne(optional = false)
    @JoinColumn(nullable = false)
    private Reward reward;


    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private REQUEST_STATUS requestStatus;
}