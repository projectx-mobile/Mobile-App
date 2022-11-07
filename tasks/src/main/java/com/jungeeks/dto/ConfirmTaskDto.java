package com.jungeeks.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmTaskDto {

    Long taskId;
    boolean photoReport;
    Long rewardPoints;
}
