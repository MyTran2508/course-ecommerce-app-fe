package com.programming.userservice.domain.dto;

import com.programming.userservice.domain.persistent.entity.Ward;
import lombok.Data;

import java.util.List;

@Data
public class DistrictDto {
    private String code;

    private String fullName;

    private String fullNameEn;

    private List<WardDto> wardList;
}
