package com.jungeeks.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * The type Parent home dto.
 */
@Data
@Builder
public class ParentHomeDto {

    private String familyId;
    private List<ChildDto> childDtos;
}
