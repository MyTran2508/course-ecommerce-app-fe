package com.programming.courseservice.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopicDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    @NotEmpty(message = "TopicName is required")

    private String name;

    private String description;

    public TopicDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
