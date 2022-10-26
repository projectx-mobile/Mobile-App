package com.jungeeks.entity;

import com.jungeeks.entity.enums.REWARD_STATUS;
import com.jungeeks.entity.enums.REWARD_TYPE;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Long points;

    @Enumerated(EnumType.STRING)
    @Column(name = "reward_status")
    private REWARD_STATUS rewardStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "reward_type")
    private REWARD_TYPE rewardType;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "reward_photo", joinColumns = @JoinColumn(name = "reward_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "path", column = @Column(name = "path"))
    })
    private List<Photo> photo;
}