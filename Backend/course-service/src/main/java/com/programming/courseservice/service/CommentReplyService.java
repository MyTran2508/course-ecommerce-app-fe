package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.main.progamming.common.util.ApiResources;
import com.main.progamming.common.util.CommonConstrant;
import com.programming.courseservice.domain.dto.CommentReplyDto;
import com.programming.courseservice.domain.mapper.CommentReplyMapper;
import com.programming.courseservice.domain.persistent.entity.CommentReply;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import com.programming.courseservice.repository.CommentReplyRepository;
import com.programming.courseservice.repository.ForumLectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReplyService extends BaseServiceImpl<CommentReply, CommentReplyDto> {

    private final CommentReplyRepository commentReplyRepository;

    private final ForumLectureRepository forumLectureRepository;

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

    // insert comment reply
    public DataResponse<String> insert(String forumLectureId, CommentReplyDto commentReplyDto) {
        ForumLecture forumLecture = forumLectureRepository.findById(forumLectureId).orElse(null);

        if (forumLecture == null) {
            // throw exception if forumLecture is null
            throw new ResourceNotFoundException(StatusMessage.DATA_NOT_FOUND);
        }

        // convert dto to entity
        CommentReply commentReply = commentReplyMapper.dtoToEntity(commentReplyDto);

        // set forumLecture to commentReply and save
        forumLecture.getCommentReplies().add(commentReply);
        forumLectureRepository.save(forumLecture);

        // return success response
        return ResponseMapper.toDataResponseSuccess(CommonConstrant.INSERT_SUCCESS);
    }
}
