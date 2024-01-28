package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.UserAnswerDto;
import com.programming.courseservice.domain.persistent.entity.UserAnswer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserAnswerMapper extends BaseMapperImpl<UserAnswer, UserAnswerDto> {
    public UserAnswerMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<UserAnswer> getEntityClass() {
        return UserAnswer.class;
    }

    @Override
    protected Class<UserAnswerDto> getDtoClass() {
        return UserAnswerDto.class;
    }
}
