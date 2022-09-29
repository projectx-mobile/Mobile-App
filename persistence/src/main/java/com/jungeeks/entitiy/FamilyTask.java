package com.jungeeks.entitiy;

import com.jungeeks.entitiy.enums.TASK_STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "family_task")
public class FamilyTask {

    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "task_id")
    private Task task;

    private LocalDateTime deadline;
    private Long points;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TASK_STATUS taskStatus;

    @Column(name = "is_daily")
    private boolean daily;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "family_task_photo", joinColumns = @JoinColumn(name = "family_task_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "path", column = @Column(name = "path"))
    })
    private List<Photo> photos;

    @ManyToMany
    @JoinTable(name = "family_task_user",
            joinColumns = {@JoinColumn(name = "family_task_id",referencedColumnName = "id")},
            inverseJoinColumns ={@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<User> users;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "family_id")
    private Family family;

}