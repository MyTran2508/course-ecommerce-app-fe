package com.programming.userservice.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class DistrictDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String fullName;

    private String fullNameEn;

    private List<WardDto> wardList;
}
