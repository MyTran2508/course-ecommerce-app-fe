package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.CourseReviewDto;
import com.programming.courseservice.domain.mapper.CourseReviewMapper;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.domain.persistent.entity.CourseProgress;
import com.programming.courseservice.domain.persistent.entity.CourseReview;
import com.programming.courseservice.repository.CourseProgressRepository;
import com.programming.courseservice.repository.CourseRepository;
import com.programming.courseservice.repository.CourseReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseReviewService extends BaseServiceImpl<CourseReview, CourseReviewDto> {
    private final CourseReviewRepository courseReviewRepository;
    private final CourseProgressRepository courseProgressRepository;
    private final CourseReviewMapper courseReviewMapper;
    private final CourseRepository courseRepository;

    @Override
    protected BaseRepository<CourseReview> getBaseRepository() {
        return courseReviewRepository;
    }

    @Override
    protected BaseMapper<CourseReview, CourseReviewDto> getBaseMapper() {
        return courseReviewMapper;
    }

    @Override
    protected Page<CourseReviewDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        String courseId = searchKeywordDto.getKeyword().get(0).trim();

        return courseReviewRepository.findCourseReviewByCourseId(courseId, pageable)
                .map(courseReview -> courseReviewMapper.entityToDto(courseReview));
    }

    @Override
    protected List<CourseReviewDto> getListSearchResults(String keyword) {
        return null;
    }

    @Override
    public DataResponse<CourseReviewDto> create(CourseReviewDto dto) {
        DataResponse<CourseReviewDto> response = super.create(dto);

        Course course = courseRepository.findById(dto.getCourseDto().getId()).get();

        course.setTotalRatings(course.getTotalRatings() + 1);

        Double totalRating = course.getCourseReviews().stream()
                .mapToDouble(CourseReview::getRating)
                .sum();

        course.setAverageRating((float) (totalRating / course.getTotalRatings()));
        courseRepository.save(course);

        return response;
    }

    @Override
    public DataResponse<CourseReviewDto> update(String id, CourseReviewDto dto) {
        DataResponse<CourseReviewDto> response = super.update(id, dto);

        Course course = courseRepository.findById(response.getData().getCourseDto().getId()).get();

        Double totalRating = course.getCourseReviews().stream()
                .mapToDouble(CourseReview::getRating)
                .sum();

        course.setAverageRating((float) (totalRating / course.getTotalRatings()));
        courseRepository.save(course);

        return response;
    }
}
