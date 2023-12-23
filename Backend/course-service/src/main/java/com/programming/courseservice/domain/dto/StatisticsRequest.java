package com.programming.courseservice.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticsRequest {
    @NotNull(message = "Target Year is required")
    private Integer targetYear;
    private Integer targetMonth;
}
