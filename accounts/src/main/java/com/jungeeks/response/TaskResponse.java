package com.jungeeks.response;

import com.jungeeks.entity.enums.TASK_STATUS;
import lombok.*;

import java.time.LocalDateTime;


/**
 * The type Task response.
 */
@Builder
@Data

public class TaskResponse {
    private String title;
    private Long point;
    private TASK_STATUS taskStatus;
    private LocalDateTime localDateTime;
}
