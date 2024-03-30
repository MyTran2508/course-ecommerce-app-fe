package com.programming.userservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String email;

    private Integer otp;

    private String newPassword;
}
