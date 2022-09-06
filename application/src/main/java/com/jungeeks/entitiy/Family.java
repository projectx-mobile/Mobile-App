package com.jungeeks.entitiy;

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
    private List<Task> task;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Request> request;

    @OneToMany(cascade = CascadeType.MERGE)
    private List<Reward> rewards;
}
