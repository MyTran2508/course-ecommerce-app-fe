package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.ContentDto;
import com.programming.courseservice.domain.dto.LectureDto;
import com.programming.courseservice.domain.dto.SectionDto;
import com.programming.courseservice.domain.mapper.ContentMapper;
import com.programming.courseservice.domain.persistent.entity.*;
import com.programming.courseservice.domain.persistent.enumrate.LectureType;
import com.programming.courseservice.repository.ContentRepository;
import com.programming.courseservice.repository.CourseRepository;
import com.programming.courseservice.repository.CourseReviewRepository;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentService extends BaseServiceImpl<Content, ContentDto> {

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    private final CourseReviewRepository courseReviewRepository;

    @Override
    protected BaseRepository<Content> getBaseRepository() {
        return contentRepository;
    }

    @Override
    protected BaseMapper<Content, ContentDto> getBaseMapper() {
        return contentMapper;
    }

    @Override
    protected Page<ContentDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<ContentDto> getListSearchResults(String keyword) {
        return null;
    }

    public DataResponse<ContentDto> getByCourseId(String courseId) {

        Optional<Content> contentOptional = contentRepository.findContentByCourseId(courseId);

        if(contentOptional.isEmpty()) {
            throw new DataNotFoundException(CourseConstrant.ErrorConstrant.CONTENT_NOT_FOUND);
        } else {
            ContentDto contentDto = contentMapper.entityToDto(contentOptional.get());

            for (SectionDto sectionDto : contentDto.getSections()) {
                for (LectureDto lectureDto : sectionDto.getLectures()) {
                    if (lectureDto.getLectureType() == LectureType.QUIZ_TEST) {
                        lectureDto.getExQuiz().setTotalQuestion(lectureDto.getExQuiz().getQuestions().size())  ;
                    }
                    if (lectureDto.getExQuiz() != null) {
                        lectureDto.getExQuiz().setQuestions(null);
                    }
                }
            }

            List<CourseReview> courseReviews = courseReviewRepository.findCourseReviewsByCourseId(courseId);


            if (courseReviews.size() > 0) {
                Map<Integer, Integer> ratingReviewByStar = new HashMap<>() {
                    {
                        put(5, 0);
                        put(4, 0);
                        put(3, 0);
                        put(2, 0);
                        put(1, 0);
                    }
                };

                for (CourseReview courseReview: courseReviews) {

                    float roundedNumber = (float) Math.ceil(courseReview.getRating());

                    Integer countRatingByStar = ratingReviewByStar.get((int) roundedNumber);
                    ratingReviewByStar.put((int) roundedNumber, countRatingByStar + 1);
                }

                Map<Integer, Integer> ratingReviewByStarPercent = new HashMap<>() {
                    {
                        put(5, ratingReviewByStar.get(5) * 100 / courseReviews.size());
                        put(4, ratingReviewByStar.get(4) * 100 / courseReviews.size());
                        put(3, ratingReviewByStar.get(3) * 100 / courseReviews.size());
                        put(2, ratingReviewByStar.get(2) * 100 / courseReviews.size());
                        put(1, ratingReviewByStar.get(1) * 100 / courseReviews.size());
                    }
                };
                contentDto.getCourse().setRatingNumbersByStar(ratingReviewByStarPercent);
            }

            return ResponseMapper.toDataResponseSuccess(contentDto);
        }
    }
}
