package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.CourseIssueReportDto;
import com.programming.courseservice.domain.persistent.entity.CourseIssueReport;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CourseIssueReportMapper extends BaseMapperImpl<CourseIssueReport, CourseIssueReportDto> {

    public CourseIssueReportMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<CourseIssueReport> getEntityClass() {
        return CourseIssueReport.class;
    }

    @Override
    protected Class<CourseIssueReportDto> getDtoClass() {
        return CourseIssueReportDto.class;
    }
}
