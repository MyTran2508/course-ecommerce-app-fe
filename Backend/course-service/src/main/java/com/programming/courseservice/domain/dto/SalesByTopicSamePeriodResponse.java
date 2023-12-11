package com.programming.courseservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesByTopicSamePeriodResponse {
    private String topicId;
    private String topicName;
    private Double targetYearTotal;
    private Double previousYearTotal;

    public Integer convertTopicIdAsInteger() {
        try {
            return Integer.parseInt(topicId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
