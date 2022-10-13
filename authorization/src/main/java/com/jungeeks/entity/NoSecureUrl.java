package com.jungeeks.entity;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoSecureUrl {
    private List<String> url;
}
