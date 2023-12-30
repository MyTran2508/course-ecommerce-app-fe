package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.CourseReviewDto;
import com.programming.courseservice.domain.persistent.entity.CourseReview;
import com.programming.courseservice.service.CourseReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public DataResponse<CourseReviewDto> add(CourseReviewDto objectDTO) {
        return super.add(objectDTO);
    }

    @Override
    public DataResponse<CourseReviewDto> update(CourseReviewDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    public ListResponse<CourseReviewDto> searchByKeyword(SearchKeywordDto searchKeywordDto) {
        return super.searchByKeyword(searchKeywordDto);
    }
}
