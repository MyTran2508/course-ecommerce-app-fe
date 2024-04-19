package com.programming.userservice.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class WardDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String fullName;

    private String fullNameEn;
}
