package com.programming.userservice.core.dto;

import lombok.*;

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
    private CategoryDto categoryDto;
}
