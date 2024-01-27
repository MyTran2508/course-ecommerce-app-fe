package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.QuestionDto;
import com.programming.courseservice.domain.persistent.entity.Question;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class QuestionMapper extends BaseMapperImpl<Question, QuestionDto> {
    public QuestionMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Question> getEntityClass() {
        return Question.class;
    }

    @Override
    protected Class<QuestionDto> getDtoClass() {
        return QuestionDto.class;
    }
}
