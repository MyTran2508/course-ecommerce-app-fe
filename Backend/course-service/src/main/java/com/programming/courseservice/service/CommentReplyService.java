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
import com.programming.courseservice.domain.dto.AvatarDto;
import com.programming.courseservice.domain.dto.CommentReplyDto;
import com.programming.courseservice.domain.mapper.CommentReplyMapper;
import com.programming.courseservice.domain.persistent.entity.CommentReply;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import com.programming.courseservice.repository.CommentReplyRepository;
import com.programming.courseservice.repository.ForumLectureRepository;
import com.programming.courseservice.utilities.communication.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentReplyService extends BaseServiceImpl<CommentReply, CommentReplyDto> {

    private final CommentReplyRepository commentReplyRepository;

    private final ForumLectureRepository forumLectureRepository;

    private final CommentReplyMapper commentReplyMapper;

    private final UserApi userApi;

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

        String forumLectureId = searchKeywordDto.getKeyword().get(0) == null ? null
                : searchKeywordDto.getKeyword().get(0).trim();

        Page<CommentReplyDto> commentReplyDtoPage = commentReplyRepository.findByForumLectureId(forumLectureId, pageable)
                .map(commentReply -> {
                    CommentReplyDto commentReplyDto = commentReplyMapper.entityToDto(commentReply);

                    // get user avatar from user service
                    ResponseEntity<AvatarDto> responseAvatar = userApi.getAvatar(commentReply.getUserName());
                    AvatarDto avatarDto = responseAvatar.getBody();

                    String rawResponse = avatarDto.toString();
                    String rawAvatar = null;

                    if (!rawResponse.contains("statusCode")) {
                        rawAvatar = avatarDto.getRawAvatar();
                    }

                    // set user avatar
                    commentReplyDto.setRawAvatar(rawAvatar);

                    return commentReplyDto;
                });

        return commentReplyDtoPage;
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
        commentReply.setForumLecture(forumLecture);

        // set forumLecture to commentReply and save
        forumLecture.getCommentReplies().add(commentReply);
        forumLectureRepository.save(forumLecture);

        // return success response
        return ResponseMapper.toDataResponseSuccess(CommonConstrant.INSERT_SUCCESS);
    }
}
