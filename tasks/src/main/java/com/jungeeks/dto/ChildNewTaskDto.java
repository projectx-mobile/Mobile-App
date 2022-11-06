package com.jungeeks.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
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
