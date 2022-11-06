package com.jungeeks.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jungeeks.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveNewTaskDto {

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
