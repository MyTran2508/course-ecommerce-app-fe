package com.programming.courseservice.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String password;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String telephone;
    private String photos;
}
