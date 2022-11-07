package com.jungeeks.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChildNewTaskDto {

    String title;
    String template;
    String description;
    Long rewardPoints;
    boolean repeatable;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime deadLine;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    LocalDateTime creation;
}
