package com.programming.userservice.domain.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private String id;

//    @NotEmpty(message = "Password is required")
//    @Size(min = 2, max = 32, message = "Password must be between 2 and 32 characters")
    private String password;

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String telephone;

    private String photos;

    private List<RoleDto> roles;

    private List<AddressDto> addresses;

    private Boolean removed;
}
