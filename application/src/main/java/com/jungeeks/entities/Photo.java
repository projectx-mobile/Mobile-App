package com.jungeeks.entities;

import lombok.*;

import java.io.File;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Photo {
//    private String name;
    private File path;
}
