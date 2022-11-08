package com.jungeeks.dto;

import com.jungeeks.entity.enums.TASK_STATUS;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
public class TaskDto {

    private Long task_id;
    private Long rewardPoints;
    private TASK_STATUS taskStatus;
    private LocalDateTime creation;
    private LocalDateTime deadline;
}