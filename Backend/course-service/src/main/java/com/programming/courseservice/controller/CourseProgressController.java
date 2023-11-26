package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.CourseProgressDto;
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
