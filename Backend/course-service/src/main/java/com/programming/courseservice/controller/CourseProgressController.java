package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.CourseProgressListDto;
import com.programming.courseservice.domain.dto.CourseProgressDto;
import com.programming.courseservice.domain.dto.StatisticsRequest;
import com.programming.courseservice.domain.persistent.entity.CourseProgress;
import com.programming.courseservice.service.CourseProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/course-progress")
@RequiredArgsConstructor
public class CourseProgressController extends BaseApiImpl<CourseProgress, CourseProgressDto> {

    private final CourseProgressService courseProgressService;

    @Override
    protected BaseService<CourseProgress, CourseProgressDto> getBaseService() {
        return courseProgressService;
    }

    @Override
    public DataResponse<String> delete(String id) {
        return super.delete(id);
    }

    @Override
    public DataResponse<CourseProgressDto> setRemoved(String id) {
        return super.setRemoved(id);
    }

    @GetMapping("/get-by-userId-courseId")
    public DataResponse<CourseProgressDto> getByUserIdAndCourseId(@RequestParam String userId,
                                                                  @RequestParam String courseId) {
        return courseProgressService.getByUserIdAndCourseId(userId, courseId);
    }

    @PostMapping("/update-current-progress")
    public DataResponse<CourseProgressDto> updateCurrentProgress(@RequestParam String userId,
                                                                 @RequestParam String courseId) {
        return courseProgressService.updateCurrentProgress(userId, courseId);
    }

    @PostMapping("/add-list")
    public DataResponse<String> addList(@RequestBody CourseProgressListDto courseProgressListDto) {
        return courseProgressService.addList(courseProgressListDto);
    }

    @GetMapping("/has-access-to-course")
    public DataResponse<Boolean> hasAccessToCourse(@RequestParam("userId") String userId,
                                                   @RequestParam("courseId") String courseId) {
        return courseProgressService.hasAccessToCourse(userId, courseId);
    }

    @PostMapping("/get-total-register-course")
    public DataResponse<Integer> getTotalRegisteredCourseByYearAndMonth(@RequestBody StatisticsRequest statisticsRequest) {
        return courseProgressService.getTotalRegisteredCourseByYearAndMonth(statisticsRequest.getTargetYear(), statisticsRequest.getTargetMonth());
    }

//    @Override
//    public DataResponse<CourseProgressDto> add(CourseProgressDto courseProgressDto) {
//        return super.add(courseProgressDto);
//    }
//
//    @Override
//    public DataResponse<CourseProgressDto> update(CourseProgressDto objectDTO, String id) {
//        return super.update(objectDTO, id);
//    }
}
