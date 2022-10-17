package com.jungeeks.entity;

import com.jungeeks.entity.enums.REQUEST_STATUS;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "reward_request")
public class RewardRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    private Reward reward;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_status")
    private REQUEST_STATUS requestStatus;
}