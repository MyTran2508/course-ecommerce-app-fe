package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.ContentDto;
import com.programming.courseservice.domain.persistent.entity.Content;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ContentMapper extends BaseMapperImpl<Content, ContentDto> {
    public ContentMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Content> getEntityClass() {
        return Content.class;
    }

    @Override
    protected Class<ContentDto> getDtoClass() {
        return ContentDto.class;
    }
}
