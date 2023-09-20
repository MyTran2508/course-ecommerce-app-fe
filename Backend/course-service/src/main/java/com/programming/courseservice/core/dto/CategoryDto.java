package com.programming.courseservice.core.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    @NotEmpty(message = "CategoryName is required")
    private String name;
    private String description;
    private List<TopicDto> topics;
}
