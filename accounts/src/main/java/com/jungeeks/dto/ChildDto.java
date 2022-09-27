package com.jungeeks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildDto {

    private String name;
    private String photoFileName;
    private Long numberOfCompletedTasks;
    private Long numberOfActiveTasks;
    private List<Long> rewardRequestIdsDtos;
    private List<Long> taskRequestIdsDtos;
}
