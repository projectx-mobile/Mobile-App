package com.jungeeks.entitiy;

import com.jungeeks.entitiy.enums.REWARD_STATUS;
import com.jungeeks.entitiy.enums.REWARD_TYPE;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToOne(orphanRemoval = true,mappedBy = "reward")
    private Request request;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "reward_photo", joinColumns = @JoinColumn(name = "reward_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "path", column = @Column(name = "path"))
    })
    private List<Photo> photo;

}