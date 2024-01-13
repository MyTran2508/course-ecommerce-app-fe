package com.programming.userservice.service;

import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.programming.userservice.domain.dto.DistrictDto;
import com.programming.userservice.domain.dto.ProvinceDto;
import com.programming.userservice.domain.dto.WardDto;
import com.programming.userservice.domain.persistent.entity.District;
import com.programming.userservice.domain.persistent.entity.Province;
import com.programming.userservice.domain.persistent.entity.Ward;
import com.programming.userservice.repository.DistrictRepository;
import com.programming.userservice.repository.ProvinceRepository;
import com.programming.userservice.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final ProvinceRepository provinceRepository;

    private final DistrictRepository districtRepository;

    private final WardRepository wardRepository;

    public ListResponse<List<ProvinceDto>> getAllProvince() {
        List<Province> provinceList = provinceRepository.findAll();
        List<ProvinceDto> provinceDtoList = new LinkedList<>();

        for (Province province : provinceList) {
            ProvinceDto provinceDto = new ProvinceDto();
            provinceDto.setCode(province.getCode());
            provinceDto.setName(province.getName());
            provinceDto.setNameEn(province.getNameEn());
            provinceDtoList.add(provinceDto);
        }

        return ResponseMapper.toListResponseSuccess(provinceDtoList);
    }

    public ListResponse<List<DistrictDto>> getDistrictByProvinceCode(String provinceCode) {
        List<District> districtList = districtRepository.findByDistrictByProvinceCode(provinceCode);
        List<DistrictDto> districtDtoList = new LinkedList<>();

        for (District district : districtList) {
            DistrictDto districtDto = new DistrictDto();
            districtDto.setCode(district.getCode());
            districtDto.setFullName(district.getFullName());
            districtDto.setFullNameEn(district.getFullNameEn());
            districtDtoList.add(districtDto);
        }

        return ResponseMapper.toListResponseSuccess(districtDtoList);
    }

    public ListResponse<List<WardDto>> getWardByDistrictCode(String districtCode) {
        List<Ward> wardList = wardRepository.findByWardByDistrictCode(districtCode);
        List<WardDto> wardDtoList = new LinkedList<>();

        for (Ward ward : wardList) {
            WardDto wardDto = new WardDto();
            wardDto.setCode(ward.getCode());
            wardDto.setFullName(ward.getFullName());
            wardDto.setFullNameEn(ward.getFullNameEn());
            wardDtoList.add(wardDto);
        }

        return ResponseMapper.toListResponseSuccess(wardDtoList);
    }
}
