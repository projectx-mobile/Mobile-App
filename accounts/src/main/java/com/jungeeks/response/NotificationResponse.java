package com.jungeeks.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationResponse {

    private LocalDateTime localDateTime;
}
