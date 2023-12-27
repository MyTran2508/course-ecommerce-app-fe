package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.CourseReviewDto;
import com.programming.courseservice.domain.persistent.entity.CourseReview;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CourseReviewMapper extends BaseMapperImpl<CourseReview, CourseReviewDto> {
    public CourseReviewMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<CourseReview> getEntityClass() {
        return CourseReview.class;
    }

    @Override
    protected Class<CourseReviewDto> getDtoClass() {
        return CourseReviewDto.class;
    }
}
