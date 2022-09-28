package com.jungeeks.response;

import com.jungeeks.entitiy.enums.TASK_STATUS;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class TaskResponse {

    private String title;
    private Long point;
    private TASK_STATUS taskStatus;
    private LocalDateTime localDateTime;
}
