package com.programming.userservice.domain.dto;

import com.programming.userservice.domain.persistent.entity.Address;
import com.programming.userservice.domain.persistent.entity.Role;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private String id;

//    @NotEmpty(message = "Password is required")
//    @Size(min = 2, max = 32, message = "Password must be between 2 and 32 characters")
    private String password;

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email is required")
    private String email;

    @NotEmpty(message = "Username is required")
    @Size(min = 4, message = "Username must be at least 4 characters")

    private String username;

    private String firstName;

    private String lastName;

    private String telephone;

    private String photos;

    private List<RoleDto> roles;

    private List<AddressDto> addresses;

    private Boolean removed;
}
