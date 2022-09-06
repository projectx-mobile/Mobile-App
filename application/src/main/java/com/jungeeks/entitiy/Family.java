package com.jungeeks.entitiy;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Family {

    @Id
    private String id;

    @OneToMany
    private List<Task> task;

    @OneToMany
    private List<Request> request;

    @OneToMany
    private List<Reward> rewards;
}
