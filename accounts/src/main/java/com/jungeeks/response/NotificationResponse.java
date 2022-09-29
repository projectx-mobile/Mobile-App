package com.jungeeks.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class NotificationResponse {

    private LocalDateTime localDateTime;
}
