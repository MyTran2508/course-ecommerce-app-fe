package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.ExQuizDto;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ExQuizMapper extends BaseMapperImpl<ExQuiz, ExQuizDto> {
    public ExQuizMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<ExQuiz> getEntityClass() {
        return ExQuiz.class;
    }

    @Override
    protected Class<ExQuizDto> getDtoClass() {
        return ExQuizDto.class;
    }
}
