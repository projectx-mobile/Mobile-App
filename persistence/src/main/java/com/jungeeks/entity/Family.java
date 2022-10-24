package com.jungeeks.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Family {

    @Id
    private String id;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "family_id", referencedColumnName = "id")
    private List<FamilyTask> tasks;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "family_rewards_requests",
    joinColumns = @JoinColumn(name = "family_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "request_id",referencedColumnName = "id")
    )
    private List<RewardRequest> requests;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "family_rewards",
            joinColumns = @JoinColumn(name = "family_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "reward_id",referencedColumnName = "id"))
    private List<Reward> rewards;
}