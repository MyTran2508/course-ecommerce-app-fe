package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.CommentReplyDto;
import com.programming.courseservice.domain.mapper.CommentReplyMapper;
import com.programming.courseservice.domain.persistent.entity.CommentReply;
import com.programming.courseservice.repository.CommentReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReplyService extends BaseServiceImpl<CommentReply, CommentReplyDto> {

    private final CommentReplyRepository commentReplyRepository;

    private final CommentReplyMapper commentReplyMapper;

    @Override
    protected BaseRepository<CommentReply> getBaseRepository() {
        return commentReplyRepository;
    }

    @Override
    protected BaseMapper<CommentReply, CommentReplyDto> getBaseMapper() {
        return commentReplyMapper;
    }

    @Override
    protected Page<CommentReplyDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<CommentReplyDto> getListSearchResults(String keyword) {
        return null;
    }
}
