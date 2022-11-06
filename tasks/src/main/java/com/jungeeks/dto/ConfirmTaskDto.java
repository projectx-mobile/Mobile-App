package com.jungeeks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmTaskDto {

    Long taskId;
    boolean photoReport;
    Long rewardPoints;
}
