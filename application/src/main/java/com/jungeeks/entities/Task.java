package com.jungeeks.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private long points;
    private Date deadLine;
    private String category;
    private TASK_STATUS taskStatus;
    private boolean daily;
    private String familyId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_photo", joinColumns = @JoinColumn(name = "task_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "name")),
            @AttributeOverride(name = "path", column = @Column(name = "path"))
    })
    private List<Photo> photo;
}
