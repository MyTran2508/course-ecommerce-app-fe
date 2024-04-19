package com.programming.userservice.controller;

import com.main.progamming.common.response.ListResponse;
import com.programming.userservice.domain.dto.DistrictDto;
import com.programming.userservice.domain.dto.ProvinceDto;
import com.programming.userservice.domain.dto.WardDto;
import com.programming.userservice.service.AddressService;
import com.programming.userservice.utilities.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/address")
public class AddressController {
    private final AddressService addressService;

    @GetMapping("get-all-province")
    @ShowOpenAPI
    public ListResponse<List<ProvinceDto>> getAllProvince() {
        return addressService.getAllProvince();
    }

    @GetMapping("get-district-by-province-code/{provinceCode}")
    @ShowOpenAPI
    public ListResponse<List<DistrictDto>> getDistrictByProvinceCode(@PathVariable String provinceCode) {
        return addressService.getDistrictByProvinceCode(provinceCode);
    }

    @GetMapping("get-ward-by-district-code/{districtCode}")
    @ShowOpenAPI
    public ListResponse<List<WardDto>> getWardByDistrictCode(@PathVariable String districtCode) {
        return addressService.getWardByDistrictCode(districtCode);
    }
}
