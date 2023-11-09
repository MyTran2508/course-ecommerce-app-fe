package com.programming.userservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.userservice.domain.dto.CourseAccessDto;
import com.programming.userservice.domain.persistent.entity.CourseAccess;
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
