package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.UserQuizDto;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserQuizMapper extends BaseMapperImpl<UserQuiz, UserQuizDto> {
    public UserQuizMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<UserQuiz> getEntityClass() {
        return UserQuiz.class;
    }

    @Override
    protected Class<UserQuizDto> getDtoClass() {
        return UserQuizDto.class;
    }
}
