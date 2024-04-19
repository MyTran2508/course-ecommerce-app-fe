package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.CourseReviewDto;
import com.programming.courseservice.domain.persistent.entity.CourseReview;
import com.programming.courseservice.service.CourseReviewService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/course-review")
public class CourseReviewController extends BaseApiImpl<CourseReview, CourseReviewDto> {

    private final CourseReviewService courseReviewService;

    @Override
    protected BaseService<CourseReview, CourseReviewDto> getBaseService() {
        return courseReviewService;
    }

    @Override
    public DataResponse<String> add(CourseReviewDto objectDTO) {
        return super.add(objectDTO);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<CourseReviewDto> update(CourseReviewDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    public ListResponse<CourseReviewDto> searchByKeyword(SearchKeywordDto searchKeywordDto) {
        return super.searchByKeyword(searchKeywordDto);
    }

    @PostMapping("/set-like")
    public DataResponse<String> setLikeReview(
            @RequestParam("courseReviewId") String courseReviewId,
            @RequestParam("username") String username,
            @RequestParam("isCancel") Boolean isCancel
    ) {
        return courseReviewService.setLike(courseReviewId, username, isCancel);
    }

    @PostMapping("/set-dislike")
    public DataResponse<String> setDisLikeReview(
            @RequestParam("courseReviewId") String courseReviewId,
            @RequestParam("username") String username,
            @RequestParam("isCancel") Boolean isCancel
    ) {
        return courseReviewService.setDislike(courseReviewId, username, isCancel);
    }

    @GetMapping("/get-by-username-courseId")
    public DataResponse<CourseReviewDto> getByCourseIdAndUserId(
            @RequestParam("courseId") String courseId,
            @RequestParam("username") String username
    ) {
        return courseReviewService.getByUsernameAndCourseId(username, courseId);
    }
}
