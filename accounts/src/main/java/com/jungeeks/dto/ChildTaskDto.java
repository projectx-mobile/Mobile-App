package com.jungeeks.dto;


import com.jungeeks.entitiy.enums.TASK_STATUS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildTaskDto {

    private String title;
    private TASK_STATUS taskStatus;
}
