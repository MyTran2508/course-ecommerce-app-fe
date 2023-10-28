package com.programming.userservice.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    @NotEmpty(message = "Id is required")
    private String id;
    @NotEmpty(message = "Name is required")
    private String name;

    public RoleDto(String value) {
    }
}
