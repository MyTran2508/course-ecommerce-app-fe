package com.programming.courseservice.core.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.core.dto.TopicDto;
import com.programming.courseservice.core.persistent.entity.Topic;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TopicMapper extends BaseMapperImpl<Topic, TopicDto> {
    public TopicMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<Topic> getEntityClass() {
        return Topic.class;
    }

    @Override
    protected Class<TopicDto> getDtoClass() {
        return TopicDto.class;
    }
}
