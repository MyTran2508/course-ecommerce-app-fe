package com.programming.courseservice.domain.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesByTopicSamePeriodResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
