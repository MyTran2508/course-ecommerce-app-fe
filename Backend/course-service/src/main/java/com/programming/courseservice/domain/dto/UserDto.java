package com.programming.courseservice.domain.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    private List<RoleDto> roles;
}
