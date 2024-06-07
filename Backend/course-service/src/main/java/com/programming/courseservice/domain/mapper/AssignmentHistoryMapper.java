package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.AssignmentHistoryDto;
import com.programming.courseservice.domain.persistent.entity.AssignmentHistory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AssignmentHistoryMapper extends BaseMapperImpl<AssignmentHistory, AssignmentHistoryDto> {

    public AssignmentHistoryMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<AssignmentHistory> getEntityClass() {
        return AssignmentHistory.class;
    }

    @Override
    protected Class<AssignmentHistoryDto> getDtoClass() {
        return AssignmentHistoryDto.class;
    }
}
