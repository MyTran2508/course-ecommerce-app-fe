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
import com.programming.courseservice.domain.persistent.entity.Content;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.domain.persistent.entity.Section;
import com.programming.courseservice.repository.ContentRepository;
import com.programming.courseservice.repository.CourseRepository;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContentService extends BaseServiceImpl<Content, ContentDto> {
    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

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
            contentDto.setCourse(null);

            for (SectionDto sectionDto : contentDto.getSections()) {
                for (LectureDto lectureDto : sectionDto.getLectures()) {
                    if(lectureDto.getExQuiz() != null) {
                        lectureDto.getExQuiz().setQuestions(null);
                    }
                }
            }

            return ResponseMapper.toDataResponseSuccess(contentDto);
        }
    }
}
