package com.jungeeks.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
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
