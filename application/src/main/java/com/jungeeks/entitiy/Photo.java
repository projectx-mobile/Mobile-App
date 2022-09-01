package com.jungeeks.entitiy;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Photo {
    private String path;
}