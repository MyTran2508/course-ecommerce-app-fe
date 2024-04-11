package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataConflictException;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.AvatarDto;
import com.programming.courseservice.domain.dto.CourseReviewDto;
import com.programming.courseservice.domain.mapper.CourseReviewMapper;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.domain.persistent.entity.CourseReview;
import com.programming.courseservice.repository.CourseRepository;
import com.programming.courseservice.repository.CourseReviewRepository;
import com.programming.courseservice.utilities.StringUtils;
import com.programming.courseservice.utilities.communication.UserApi;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        Long currentTime = System.currentTimeMillis();

        return courseReviewRepository.filterCourseReview(courseId, currentTime, pageable)
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
                    courseReviewDto.setUserAvatar(rawAvatar);
                    courseReviewDto.setCourse(null);

                    // get current username from token
                    String username = SystemUtil.getCurrentUsername();

                    // set user liking
                    String[] userLikeArr = courseReview.getUserLikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
                    Boolean isUserLiking = Arrays.stream(userLikeArr)
                            .anyMatch(userLike -> Objects.equals(userLike, username));
                    courseReviewDto.setIsUserLiking(isUserLiking);

                    // set user disliking
                    String[] userDislikeArr = courseReview.getUserDislikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
                    Boolean isUserDisliking = Arrays.stream(userDislikeArr)
                            .anyMatch(userDislike -> Objects.equals(userDislike, username));
                    courseReviewDto.setIsUserDisliking(isUserDisliking);

                    return courseReviewDto;
                });
    }

    // get course review by course id and user id
    public DataResponse<CourseReviewDto> getByUsernameAndCourseId(String userName, String courseId) {

        CourseReview courseReview = courseReviewRepository.findCourseReviewsByUsernameAndCourseId(userName, courseId);

        if (courseReview == null) {
            throw new DataNotFoundException(CourseConstrant.ErrorConstrant.COURSE_REVIEW_NOT_FOUND);
        }

        courseReview.setCourse(null);
        CourseReviewDto courseReviewDto = courseReviewMapper.entityToDto(courseReview);

        // get user avatar from user service
        ResponseEntity<AvatarDto> responseAvatar = userApi.getAvatar(courseReview.getUsername());
        AvatarDto avatarDto = responseAvatar.getBody();
        String rawResponse = avatarDto.toString();
        String rawAvatar = null;
        if (!rawResponse.contains("statusCode")) {
            rawAvatar = avatarDto.getRawAvatar();
        }

        // set user avatar
        courseReviewDto.setUserAvatar(rawAvatar);

        return ResponseMapper.toDataResponseSuccess(courseReviewDto);
    }

    @Override
    protected List<CourseReviewDto> getListSearchResults(String keyword) {
        return null;
    }

    @Override
    public DataResponse<String> create(CourseReviewDto courseReviewDto) {
        CourseReview courseReview = courseReviewRepository.findCourseReviewsByUsernameAndCourseId(courseReviewDto.getUsername(), courseReviewDto.getCourse().getId());

        if (courseReview != null) {
            throw new DataConflictException("You have already reviewed this course");
        }
        // check rating
        if (courseReviewDto.getRating() > 5) {
            throw new DataConflictException("Rating must be between 1 and 5");
        }

        courseReviewDto.setLikeAmount(0);
        courseReviewDto.setDisLikeAmount(0);
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

    // set like review
    @Transactional
    @SuppressWarnings("unchecked")
    public DataResponse<String> setLike(String courseReviewId, String userName, Boolean isCancel) {

        CourseReview courseReview = courseReviewRepository.findById(courseReviewId).get();
        Integer likeAmount = courseReview.getLikeAmount();
        String userLikes = courseReview.getUserLikes();
        String[] userLikeArr = courseReview.getUserLikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
        Boolean isUserLiking = Arrays.stream(userLikeArr)
                .anyMatch(userLike -> Objects.equals(userLike, userName));

        String userDislikes = courseReview.getUserDislikes();
        Integer disLikeAmount = courseReview.getDisLikeAmount();
        String[] userDislikeArr = courseReview.getUserDislikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
        Boolean isUserDisliking = Arrays.stream(userDislikeArr)
                .anyMatch(userDislike -> Objects.equals(userDislike, userName));

        // has already like and cancel like
        if (isCancel && isUserLiking) {
            userLikes = userLikes.replace(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE, "")
                    .replace(userName, "");
            likeAmount--;
        }
        // not like and like
        if (!isCancel && !isUserLiking) {
            userLikes = userLikes.concat(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
            likeAmount++;

            if (isUserDisliking) {
                userDislikes = userDislikes.replace(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE, "")
                        .replace(userName, "");
                disLikeAmount--;
                courseReview.setDisLikeAmount(disLikeAmount);
            }
        }
        courseReview.setUserLikes(userLikes);
        courseReview.setLikeAmount(likeAmount);
        courseReview.setUserDislikes(userDislikes);

        courseReviewRepository.save(courseReview);
        return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.UPDATE_SUCCESS);
    }

    // set dislike review
    @Transactional
    @SuppressWarnings("unchecked")
    public DataResponse<String> setDislike(String courseReviewId, String userName, Boolean isCancel) {
        CourseReview courseReview = courseReviewRepository.findById(courseReviewId).get();

        Integer likeAmount = courseReview.getLikeAmount();
        String userLikes = courseReview.getUserLikes();
        String[] userLikeArr = courseReview.getUserLikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
        Boolean isUserLiking = Arrays.stream(userLikeArr)
                .anyMatch(userLike -> Objects.equals(userLike, userName));

        Integer disLikeAmount = courseReview.getDisLikeAmount();
        String userDislikes = courseReview.getUserDislikes();
        String[] userDislikeArr = courseReview.getUserDislikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
        Boolean isUserDisliking = Arrays.stream(userDislikeArr)
                .anyMatch(userDislike -> Objects.equals(userDislike, userName));

        // has already dislike and cancel dislike
        if (isCancel && isUserDisliking) {
            userDislikes = userDislikes.replace(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE, "")
                    .replace(userName, "");
            disLikeAmount--;
        }
        // not dislike and dislike
        if (!isCancel && !isUserDisliking) {
            userDislikes = userDislikes.concat(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
            disLikeAmount++;

            if (isUserLiking) {
                userLikes = userLikes.replace(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE, "")
                        .replace(userDislikes, "");
                likeAmount--;
                courseReview.setLikeAmount(likeAmount);
            }
        }
        courseReview.setUserLikes(userLikes);
        courseReview.setUserDislikes(userDislikes);
        courseReview.setDisLikeAmount(disLikeAmount);

        courseReviewRepository.save(courseReview);
        return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.UPDATE_SUCCESS);
    }
}
