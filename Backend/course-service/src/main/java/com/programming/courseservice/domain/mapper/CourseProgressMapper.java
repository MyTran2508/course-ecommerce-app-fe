package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.CourseProgressDto;
import com.programming.courseservice.domain.persistent.entity.CourseProgress;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CourseProgressMapper extends BaseMapperImpl<CourseProgress, CourseProgressDto> {
    public CourseProgressMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
    @Override
    protected Class<CourseProgress> getEntityClass() {
        return CourseProgress.class;
    }
    @Override
    protected Class<CourseProgressDto> getDtoClass() {
        return CourseProgressDto.class;
    }
}
