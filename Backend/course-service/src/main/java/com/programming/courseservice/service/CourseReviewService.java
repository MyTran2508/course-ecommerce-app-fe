package com.programming.courseservice.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataConflictException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.AvatarDto;
import com.programming.courseservice.domain.dto.CourseReviewDto;
import com.programming.courseservice.domain.mapper.CourseReviewMapper;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.domain.persistent.entity.CourseReview;
import com.programming.courseservice.repository.CourseRepository;
import com.programming.courseservice.repository.CourseReviewRepository;
import com.programming.courseservice.utilities.communication.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseReviewService extends BaseServiceImpl<CourseReview, CourseReviewDto> {

    private final CourseReviewRepository courseReviewRepository;

    private final CourseReviewMapper courseReviewMapper;

    private final CourseRepository courseRepository;

    private final UserApi userApi;

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

        // get course id
        String courseId = searchKeywordDto.getKeyword().get(0).trim();

        return courseReviewRepository.findCourseReviewByCourseId(courseId, pageable)
                .map(courseReview ->  {
                    CourseReviewDto courseReviewDto = courseReviewMapper.entityToDto(courseReview);

                    // todo: filter sensitive data

                    // get user avatar from user service
                    ResponseEntity<AvatarDto> responseAvatar = userApi.getAvatar(courseReview.getUsername());
                    AvatarDto avatarDto = responseAvatar.getBody();
                    String rawResponse = avatarDto.toString();
                    String rawAvatar = null;
                    if (!rawResponse.contains("statusCode")) {
                        rawAvatar = avatarDto.getRawAvatar();
                    }

                    // set user avatar
                    courseReviewDto.setCourse(null);
                    courseReviewDto.setUserAvatar(rawAvatar);

                    return courseReviewDto;
                });
    }

    @Override
    protected List<CourseReviewDto> getListSearchResults(String keyword) {
        return null;
    }

    @Override
    public DataResponse<String> create(CourseReviewDto courseReviewDto) {
        CourseReview courseReview = courseReviewRepository.findCourseReviewsByUsername(courseReviewDto.getUsername());
        if (courseReview != null) {
            throw new DataConflictException("You have already reviewed this course");
        }
        // check rating
        if (courseReviewDto.getRating() > 5) {
            throw new DataConflictException("Rating must be between 1 and 5");
        }

        // save course review
        DataResponse<String> response = super.create(courseReviewDto);

        // get course
        Course course = courseRepository.findById(courseReviewDto.getCourse().getId()).get();

        // set total ratings
        if (course.getTotalRatings() == null) {
            course.setTotalRatings(0);
        }
        course.setTotalRatings(course.getTotalRatings() + 1);

        // Calculate average rating
        Double totalRating = course.getCourseReviews().stream()
                .mapToDouble(CourseReview::getRating)
                .sum();
        course.setAverageRating((float) (totalRating / course.getTotalRatings()));

        // save course
        courseRepository.save(course);

        return response;
    }

    @Override
    public DataResponse<CourseReviewDto> update(String id, CourseReviewDto courseReviewDto) {
        DataResponse<CourseReviewDto> response = super.update(id, courseReviewDto);

        // check rating
        if (courseReviewDto.getRating() > 5) {
            throw new DataConflictException("Rating must be between 1 and 5");
        }

        // get course
        Course course = courseRepository.findById(response.getData().getCourse().getId()).get();

        // calculate average rating
        Double totalRating = course.getCourseReviews().stream()
                .mapToDouble(CourseReview::getRating)
                .sum();
        course.setAverageRating((float) (totalRating / course.getTotalRatings()));

        // save course
        courseRepository.save(course);
        return response;
    }
}
