package com.jungeeks.entities;

import com.jungeeks.entities.enums.TASK_STATUS;
import com.jungeeks.entities.enums.TASK_TYPE;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String description;
    private long points;
    private Date deadline;
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TASK_STATUS taskStatus;
    private boolean daily;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type")
    private TASK_TYPE taskType;

    @Column(name = "family_id")
    private String familyId;

    @ManyToMany(mappedBy = "tasks")
    private List<User> users = new java.util.ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_photo", joinColumns = @JoinColumn(name = "task_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "path", column = @Column(name = "path"))
    })
    private List<Photo> photo;

}