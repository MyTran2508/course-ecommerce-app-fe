package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.CourseDto;
import com.programming.courseservice.domain.dto.SearchCourseDto;
import com.programming.courseservice.domain.mapper.CourseMapper;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService extends BaseServiceImpl<Course, CourseDto> {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    @Override
    protected BaseRepository<Course> getBaseRepository() {
        return courseRepository;
    }

    @Override
    protected BaseMapper<Course, CourseDto> getBaseMapper() {
        return courseMapper;
    }

    @Override
    protected Page<CourseDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<CourseDto> getListSearchResults(String keyword) {
        return null;
    }

    public ListResponse<List<CourseDto>> getNewestCourse(String topicId, Integer size) {
        Sort sortCourse = Sort.by(Sort.Direction.DESC, "created");
        List<Course> courseList = courseRepository.getCourseByTopicId(topicId, sortCourse);
        List<CourseDto> resultList = courseList.stream()
                .limit(size).map((course) -> courseMapper.entityToDto(course))
                .collect(Collectors.toList());
        return ResponseMapper.toListResponseSuccess(resultList);
    }

    @Override
    public ListResponse<CourseDto> getAll() {
        return super.getAll();
    }

    public ListResponse<CourseDto> getPopularCourse(String topicId, Integer size) {
        return null;
    }

    public ListResponse<CourseDto> getFavoritesCourse(String topicId, Integer size) {
        return null;
    }

    public ListResponse<CourseDto> getFiltedCourse(SearchCourseDto searchCourseDto) {
        return null;
    }
}
