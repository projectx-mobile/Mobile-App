package com.jungeeks.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;


/**
 * The type Child dto.
 */
@Data
@Builder
public class ChildDto {

    private String name;
    private String photoFileName;
    private Long numberOfCompletedTasks;
    private Long numberOfActiveTasks;
    private List<Long> rewardRequestIdsDtos;
    private List<Long> taskRequestIdsDtos;
}
