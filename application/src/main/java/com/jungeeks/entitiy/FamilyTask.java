package com.jungeeks.entitiy;

import com.jungeeks.entitiy.enums.TASK_STATUS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "family_tasks")
public class FamilyTask {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private LocalDateTime deadline;
    private Long points;
    private boolean daily;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TASK_STATUS taskStatus;
}



