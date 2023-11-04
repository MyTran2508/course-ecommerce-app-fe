package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.SectionDto;
import com.programming.courseservice.domain.persistent.entity.Section;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SectionMapper extends BaseMapperImpl<Section, SectionDto> {
    public SectionMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Section> getEntityClass() {
        return Section.class;
    }

    @Override
    protected Class<SectionDto> getDtoClass() {
        return SectionDto.class;
    }
}
