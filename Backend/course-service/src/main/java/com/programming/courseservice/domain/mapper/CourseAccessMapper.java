package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.CourseAccessDto;
import com.programming.courseservice.domain.persistent.entity.CourseAccess;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CourseAccessMapper extends BaseMapperImpl<CourseAccess, CourseAccessDto> {
    public CourseAccessMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<CourseAccess> getEntityClass() {
        return CourseAccess.class;
    }

    @Override
    protected Class<CourseAccessDto> getDtoClass() {
        return CourseAccessDto.class;
    }
}
