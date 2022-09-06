package com.jungeeks.entitiy;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Photo {
    private String path;
    private LocalDateTime creationDate;
}