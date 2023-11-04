package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.CourseDto;
import com.programming.courseservice.domain.persistent.entity.Course;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper extends BaseMapperImpl<Course, CourseDto> {
    public CourseMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Course> getEntityClass() {
        return Course.class;
    }

    @Override
    protected Class<CourseDto> getDtoClass() {
        return CourseDto.class;
    }
}
