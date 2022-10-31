package com.jungeeks.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data

public class NotificationResponse {
//    @DateTimeFormat(iso = DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm")
    private LocalDateTime localDateTime;
}
