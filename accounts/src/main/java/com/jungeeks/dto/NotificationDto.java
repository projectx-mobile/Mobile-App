package com.jungeeks.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
public class NotificationDto {

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime localDateTime;
}
