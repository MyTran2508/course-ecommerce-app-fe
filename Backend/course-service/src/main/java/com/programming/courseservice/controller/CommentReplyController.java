package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.ApiResources;
import com.programming.courseservice.domain.dto.CommentReplyDto;
import com.programming.courseservice.domain.persistent.entity.CommentReply;
import com.programming.courseservice.service.CommentReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/comment-reply")
public class CommentReplyController extends BaseApiImpl<CommentReply, CommentReplyDto> {

    private final CommentReplyService commentReplyService;

    @Override
    protected BaseService<CommentReply, CommentReplyDto> getBaseService() {
        return commentReplyService;
    }

    @PostMapping(ApiResources.ADD + "/{forumLectureId}")
    public DataResponse<String> insert(@PathVariable("forumLectureId") String forumLectureId,
            @RequestBody CommentReplyDto commentReplyDto) {
        return commentReplyService.insert(forumLectureId, commentReplyDto);
    }

    @Override
    public DataResponse<CommentReplyDto> update(CommentReplyDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    public ListResponse<CommentReplyDto> searchByKeyword(SearchKeywordDto searchKeywordDto) {
        return super.searchByKeyword(searchKeywordDto);
    }
}
