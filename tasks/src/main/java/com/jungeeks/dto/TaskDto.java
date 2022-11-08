package com.jungeeks.dto;

import com.jungeeks.entity.enums.TASK_STATUS;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String title;
    private Long rewardPoints;
    private TASK_STATUS taskStatus;
    private LocalDateTime creation;
    private LocalDateTime deadline;
}