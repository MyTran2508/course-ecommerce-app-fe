package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.AssignmentDto;
import com.programming.courseservice.domain.persistent.entity.Assignment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AssignmentMapper extends BaseMapperImpl<Assignment, AssignmentDto> {

    public AssignmentMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Assignment> getEntityClass() {
        return Assignment.class;
    }

    @Override
    protected Class<AssignmentDto> getDtoClass() {
        return AssignmentDto.class;
    }
}
