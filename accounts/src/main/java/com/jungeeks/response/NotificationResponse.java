package com.jungeeks.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Notification response.
 */
@Builder
@Data
public class NotificationResponse {
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime localDateTime;
}
