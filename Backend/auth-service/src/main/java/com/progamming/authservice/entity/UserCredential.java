package com.progamming.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredential {
    private String userName;
    private String email;
    private String password;
    private Set<Role> roles;
}
