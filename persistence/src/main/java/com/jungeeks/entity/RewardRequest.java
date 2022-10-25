package com.jungeeks.entity;

import com.jungeeks.entity.enums.REQUEST_STATUS;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "reward_request_photo", joinColumns = @JoinColumn(name = "reward_request_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "path", column = @Column(name = "path"))
    })
    private List<Photo> photos;
}