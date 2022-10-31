package com.jungeeks.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ParentHomeDto {

    private String familyId;
    private List<ChildDto> childDtos;
}
