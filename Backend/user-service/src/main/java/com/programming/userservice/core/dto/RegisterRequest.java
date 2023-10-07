package com.programming.userservice.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String password;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String telephone;
    private String photos;
}
