package com.programming.userservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatictisSamePeriodDto {
    private Integer month;
    private Double targetYearTotal;
    private Double previousYearTotal;
}
