package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.CourseAccessDto;
import com.programming.courseservice.domain.dto.CourseAccessListDto;
import com.programming.courseservice.domain.persistent.entity.CourseAccess;
import com.programming.courseservice.service.CourseAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/course-access")
public class CourseAccessController extends BaseApiImpl<CourseAccess, CourseAccessDto> {
    private final CourseAccessService courseAccessService;
    @Override
    protected BaseService<CourseAccess, CourseAccessDto> getBaseService() {
        return courseAccessService;
    }

    @Override
    public DataResponse<String> delete(String id) {
        return super.delete(id);
    }

    @GetMapping("/has-access-to-course")
    public DataResponse<Boolean> hasAccessToCourse(@RequestParam("userId") String userId,
                                                   @RequestParam("courseId") String courseId) {
        return courseAccessService.hasAccessToCourse(userId, courseId);
    }

    @PostMapping("/add-list")
    public DataResponse<String> addList(@RequestBody CourseAccessListDto courseAccessListDto) {
        return courseAccessService.addList(courseAccessListDto);
    }
}
