package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.*;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.service.CourseService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/course")
public class CourseController extends BaseApiImpl<Course, CourseDto> {

    private final CourseService courseService;

    @Override
    protected BaseService<Course, CourseDto> getBaseService() {
        return courseService;
    }

    @Override
    public ListResponse<CourseDto> getAll() {
        return super.getAll();
    }

    @Override
    @ShowOpenAPI
    public DataResponse<CourseDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    @ShowOpenAPI
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public DataResponse<String> add(CourseDto objectDTO) {
        return super.add(objectDTO);
    }

    @ShowOpenAPI
    @PostMapping("/images")
    public DataResponse<String> uploadCourseImage(@RequestParam("file") MultipartFile file) {
        return courseService.uploadCourseImage(file);
    }

    @ShowOpenAPI
    @GetMapping("/download")
    public ResponseEntity<?> loadFile(@RequestParam("path") String path) {
        return courseService.loadFile(path);
    }

    @ShowOpenAPI
    @PostMapping("/videos")
    public DataResponse<String> uploadCourseVideo(@RequestParam("file") MultipartFile file) {
        return courseService.uploadCourseVideo(file);
    }

    @Override
    @ShowOpenAPI
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public DataResponse<CourseDto> update(CourseDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @GetMapping("/newest/{topic-id}/{size}")
    @ShowOpenAPI
    public ListResponse<List<CourseDto>> getNewestCourse(@PathVariable("topic-id") String topicId, @PathVariable("size") Integer size) {
        return courseService.getNewestCourse(topicId, size);
    }

    @GetMapping("/popular/{topic-id}/{size}")
    public ListResponse<List<CourseDto>> getPopularCourse(@PathVariable("topic-id") String topicId, @PathVariable("size") Integer size) {
        return courseService.getPopularCourse(topicId, size);
    }

    @PostMapping("/filter")
    public ListResponse<CourseDto> getFiltedCourse(@RequestBody SearchCourseDto searchCourseDto) {
        return courseService.getFiltedCourse(searchCourseDto);
    }

    @GetMapping("/get-all-by-user-id")
    public ListResponse<CourseDto> getCourseAccessByUserId(
            @RequestParam("userId") String userId,
            @RequestParam("pageIndex") Integer pageIndex,
            @RequestParam("pageSize") Integer pageSize
    ) {
        return courseService.getCourseAccessByUserId(userId, pageIndex, pageSize);
    }

    @PostMapping("/update-approved")
    public DataResponse<String> updateIsApproved(@RequestParam("id") String courseId,
                                                 @RequestParam("isApproved") Boolean isApproved,
                                                 @RequestBody CourseIssueReportDto courseIssueReportDto) {
        return courseService.updateIsApproved(courseId, isApproved, courseIssueReportDto);
    }

    @PostMapping("/update-awaiting-approval")
    public DataResponse<String> updateAwaitingApproval(@RequestParam("id") String courseId,
                                                           @RequestParam("isAwaitingApproval") Boolean isAwaitingApproval) {
        return courseService.updateAwaitingApproval(courseId, isAwaitingApproval);
    }

    @Override
    public ListResponse<CourseDto> searchByKeyword(SearchKeywordDto searchKeywordDto) {
        /*
         * List<String> keyword:
         * index 1: key of name or subTitle
         * index 2: creator (username)
         * index 3: isApproved (true/false/null)
         * index 4: isAwaitingApproval (true/false/null)
         * index 5: isAwaitingApproval (true/false/null)
         */
        return super.searchByKeyword(searchKeywordDto);
    }

    @PostMapping("/get-total-approved-course")
    public DataResponse<Integer> getTotalApprovedCourseByYearAndMonth(@RequestBody StatisticsRequest statisticsRequest) {
        return courseService.getTotalApprovedCourseByYearAndMonth(statisticsRequest.getTargetYear(), statisticsRequest.getTargetMonth());
    }

    @GetMapping("/sales-by-topics")
    public DataResponse<List<SalesByTopicResponse>> getSalesByTopics(@RequestParam("targetYear") Integer targetYear) {
        return courseService.getSalesByTopics(targetYear);
    }

    @GetMapping("/sales-same-period-by-topics")
    public DataResponse<List<SalesByTopicSamePeriodResponse>> getSalesSamePeriodByTopics(@RequestParam("targetYear") Integer targetYear) {
        return courseService.getSalesSamePeriodByTopics(targetYear);
    }
}
