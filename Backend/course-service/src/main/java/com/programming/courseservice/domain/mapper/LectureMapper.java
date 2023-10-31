package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.LectureDto;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class LectureMapper extends BaseMapperImpl<Lecture, LectureDto> {
    public LectureMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Lecture> getEntityClass() {
        return Lecture.class;
    }

    @Override
    protected Class<LectureDto> getDtoClass() {
        return LectureDto.class;
    }
}
