package com.programming.courseservice.core.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    @NotEmpty(message = "TopicId is required")
    private String id;
    @NotEmpty(message = "TopicName is required")
    private String name;
    private String description;
}
