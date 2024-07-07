package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.CourseReviewDto;
import com.programming.courseservice.domain.dto.UserLogDto;
import com.programming.courseservice.domain.mapper.CourseReviewMapper;
import com.programming.courseservice.domain.persistent.entity.CourseReview;
import com.programming.courseservice.domain.persistent.enumrate.ActionName;
import com.programming.courseservice.domain.persistent.enumrate.ActionObject;
import com.programming.courseservice.repository.CourseReviewRepository;
import com.programming.courseservice.service.CourseReviewService;
import com.programming.courseservice.service.UserLogService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import com.programming.courseservice.utilities.communication.UserApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/course-review")
public class CourseReviewController extends BaseApiImpl<CourseReview, CourseReviewDto> {

    private final CourseReviewService courseReviewService;

    private final CourseReviewRepository courseReviewRepository;

    private final UserLogService userLogService;

    private final CourseReviewMapper courseReviewMapper;

    private final UserApi userApi;

    @Override
    protected BaseService<CourseReview, CourseReviewDto> getBaseService() {
        return courseReviewService;
    }

    @Override
    public DataResponse<String> add(CourseReviewDto objectDTO) {
        DataResponse<String> response = super.add(objectDTO);

        String stResult = response.getData();
        String reviewId = stResult.split(": ")[1].trim();
        CourseReview entity = courseReviewRepository.findById(reviewId).orElse(null);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Course ID: " + entity.getCourse().getId() + "; CourseReview ID: " + entity.getId())
                .actionObject(ActionObject.COURSE_REVIEW)
                .actionName(ActionName.CREATE)
                .description(userLogService.writePersistLog(CourseReview.class, entity, true, 0))
                .build();
        System.out.println(userLogDto);
        userApi.addLog(userLogDto);

        return response;
    }

    @Override
    @ShowOpenAPI
    public DataResponse<CourseReviewDto> update(CourseReviewDto objectDTO, String id) {

        CourseReview savedCourseReview = courseReviewRepository.findById(id).orElse(null);
        if (savedCourseReview == null) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }

        CourseReview oldCourseReviewClone = SerializationUtils.clone(savedCourseReview);
        System.out.println("prefix user: " + oldCourseReviewClone);

        DataResponse<CourseReviewDto> response = super.update(objectDTO, id);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Course ID: " + oldCourseReviewClone.getCourse().getId()
                        + "; CourseReview ID: " + oldCourseReviewClone.getId())
                .actionObject(ActionObject.COURSE_REVIEWS)
                .actionName(ActionName.UPDATE)
                .description(userLogService.writeUpdateLog(CourseReview.class, oldCourseReviewClone,
                        courseReviewMapper.dtoToEntity(response.getData()), true, 0))
                .build();

        userApi.addLog(userLogDto);

        return response;
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
