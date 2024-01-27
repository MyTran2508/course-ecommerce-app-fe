package com.programming.userservice.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProvinceDto {
    private String code;

    private String name;

    private String NameEn;

    private List<DistrictDto> districtList;
}
