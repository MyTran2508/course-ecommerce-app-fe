package com.programming.courseservice.core.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    @NotEmpty(message = "TopicName is required")
    private String name;
    private String description;
}