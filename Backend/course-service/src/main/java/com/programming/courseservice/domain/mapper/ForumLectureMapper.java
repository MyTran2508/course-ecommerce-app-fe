package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.ForumLectureDto;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ForumLectureMapper extends BaseMapperImpl<ForumLecture, ForumLectureDto> {
    public ForumLectureMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<ForumLecture> getEntityClass() {
        return ForumLecture.class;
    }

    @Override
    protected Class<ForumLectureDto> getDtoClass() {
        return ForumLectureDto.class;
    }
}
