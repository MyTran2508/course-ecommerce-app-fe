package com.programming.courseservice.domain.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LevelDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String description;
}
