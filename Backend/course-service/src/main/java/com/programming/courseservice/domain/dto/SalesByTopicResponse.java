package com.programming.courseservice.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesByTopicResponse {
    private String topicId;
    private String topicName;
    private Double totalPrice;


    public Integer convertTopicIdAsInteger() {
        try {
            return Integer.parseInt(topicId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
