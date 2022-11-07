package com.jungeeks.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentNewTaskDto {

    List<Long> userIds;
    String title;
    String template;
    String description;
    Long rewardPoints;
    Long penaltyPoints;
    boolean repeatable;
    boolean photoReport;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime deadLine;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime creation;
}
