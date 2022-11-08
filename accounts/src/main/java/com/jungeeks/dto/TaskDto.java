package com.jungeeks.dto;

import com.jungeeks.entity.enums.TASK_STATUS;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
public class TaskDto {

    private String title;
    private Long point;
    private TASK_STATUS taskStatus;
    private LocalDateTime localDateTime;
}
