package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.ForumLectureDto;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import com.programming.courseservice.service.ForumLectureService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses/forum-lecture")
@RequiredArgsConstructor
public class ForumLectureController extends BaseApiImpl<ForumLecture, ForumLectureDto> {

    private final ForumLectureService forumLectureService;

    @Override
    protected BaseService getBaseService() {
        return forumLectureService;
    }

    @Override
    @ShowOpenAPI
    public DataResponse<String> add(ForumLectureDto objectDTO) {
        return super.add(objectDTO);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<ForumLectureDto> update(ForumLectureDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    @ShowOpenAPI
    public ListResponse<ForumLectureDto> searchByKeyword(SearchKeywordDto searchKeywordDto) {
        return super.searchByKeyword(searchKeywordDto);
    }

    @PostMapping("/set-like")
    public DataResponse<ForumLectureDto> setLikeReview(
            @RequestParam("id") String id,
            @RequestParam("username") String username,
            @RequestParam("isCancel") Boolean isCancel
    ) {
        return forumLectureService.setLike(id, username, isCancel);
    }

    @PostMapping("/set-dislike")
    public DataResponse<ForumLectureDto> setDisLikeReview(
            @RequestParam("id") String id,
            @RequestParam("username") String username,
            @RequestParam("isCancel") Boolean isCancel
    ) {
        return forumLectureService.setDislike(id, username, isCancel);
    }
}
