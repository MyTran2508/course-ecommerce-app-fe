package com.programming.userservice.core.dto;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private String password;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String telephone;
    private String photos;
    private Set<RoleDto> roles;
}
