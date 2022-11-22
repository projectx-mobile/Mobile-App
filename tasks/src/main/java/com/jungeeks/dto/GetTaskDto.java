package com.jungeeks.dto;

import com.jungeeks.entity.enums.TASK_STATUS;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class GetTaskDto {

    Long id;
    String title;
    Long penaltyPoints;
    Long rewardPoints;
    TASK_STATUS taskStatus;
    LocalDateTime deadLine;
}
