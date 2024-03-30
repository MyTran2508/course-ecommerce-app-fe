package com.programming.courseservice.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Id is required")
    private String id;

    @NotEmpty(message = "Name is required")
    private String name;
}
