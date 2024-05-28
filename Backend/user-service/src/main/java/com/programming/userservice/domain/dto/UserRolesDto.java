package com.programming.userservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String email;

    private List<RoleDto> roles;

    private List<RoleDetailDto> roleDetailDtos;
}
