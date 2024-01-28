package com.programming.courseservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String requirements;

    private String details;

    private String targetConsumers;
}
