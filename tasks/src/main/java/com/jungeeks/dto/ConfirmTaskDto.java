package com.jungeeks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmTaskDto {

    Long taskId;
    boolean photoReport;
    Long rewardPoints;
}
