package com.programming.courseservice.domain.mapper;

import com.main.progamming.common.model.BaseMapperImpl;
import com.programming.courseservice.domain.dto.CommentReplyDto;
import com.programming.courseservice.domain.persistent.entity.CommentReply;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentReplyMapper extends BaseMapperImpl<CommentReply, CommentReplyDto> {
    public CommentReplyMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    protected Class<CommentReply> getEntityClass() {
        return CommentReply.class;
    }

    @Override
    protected Class<CommentReplyDto> getDtoClass() {
        return CommentReplyDto.class;
    }
}
