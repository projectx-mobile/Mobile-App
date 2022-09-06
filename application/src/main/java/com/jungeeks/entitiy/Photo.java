package com.jungeeks.entitiy;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Photo {

    private String path;
    private LocalDateTime creationDate;
}