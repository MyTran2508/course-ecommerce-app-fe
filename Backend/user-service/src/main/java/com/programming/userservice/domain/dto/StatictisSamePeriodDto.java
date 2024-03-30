package com.programming.userservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatictisSamePeriodDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer month;

    private Double targetYearTotal;

    private Double previousYearTotal;
}
