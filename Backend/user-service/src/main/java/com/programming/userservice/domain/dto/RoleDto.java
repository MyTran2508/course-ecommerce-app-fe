package com.programming.userservice.domain.dto;

import com.programming.userservice.domain.persistent.entity.RoleDetail;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private String id;

    private String name;

    private String description;

    private List<RoleDetailDto> roleDetails;

    public RoleDto(String name) {
        this.name = name;
    }
}
