package com.jungeeks.entities;

import com.jungeeks.entities.enums.REQUEST_STATUS;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Request implements Serializable {

    @ToString.Include
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

}