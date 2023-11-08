package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.SectionDto;
import com.programming.courseservice.domain.persistent.entity.Section;
import com.programming.courseservice.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/section")
public class SectionController extends BaseApiImpl<Section, SectionDto> {
    private final SectionService sectionService;
    @Override
    protected BaseService<Section, SectionDto> getBaseService() {
        return sectionService;
    }
    @Override
    public DataResponse<SectionDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    public DataResponse<SectionDto> add(SectionDto objectDTO) {
        return super.add(objectDTO);
    }

    @Override
    public DataResponse<SectionDto> update(SectionDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }
}
