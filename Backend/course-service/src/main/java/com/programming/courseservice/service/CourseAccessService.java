package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.CourseAccessDto;
import com.programming.courseservice.domain.dto.CourseAccessListDto;
import com.programming.courseservice.domain.dto.CourseDto;
import com.programming.courseservice.domain.dto.CourseProgressDto;
import com.programming.courseservice.domain.mapper.CourseAccessMapper;
import com.programming.courseservice.domain.mapper.CourseMapper;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.domain.persistent.entity.CourseAccess;
import com.programming.courseservice.domain.persistent.entity.Section;
import com.programming.courseservice.repository.CourseAccessRepository;
import com.programming.courseservice.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseAccessService extends BaseServiceImpl<CourseAccess, CourseAccessDto> {
    private final CourseAccessRepository courseAccessRepository;
    private final CourseAccessMapper courseAccessMapper;
    private final CourseRepository courseRepository;
    private final CourseProgressService courseProgressService;
    private final CourseMapper courseMapper;
    @Override
    protected BaseRepository<CourseAccess> getBaseRepository() {
        return courseAccessRepository;
    }

    @Override
    protected BaseMapper<CourseAccess, CourseAccessDto> getBaseMapper() {
        return courseAccessMapper;
    }

    @Override
    protected Page<CourseAccessDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<CourseAccessDto> getListSearchResults(String keyword) {
        return null;
    }

    public DataResponse<Boolean> hasAccessToCourse(String userId, String courseId) {
        Optional<CourseAccess> optionalCourseAccess = courseAccessRepository.findByUserIdAndCourseId(userId, courseId);
        if(optionalCourseAccess.isPresent()) {
            return ResponseMapper.toDataResponseSuccess(true);
        } else {
            return ResponseMapper.toDataResponseSuccess(false);
        }
    }

    @Transactional
    public DataResponse<String> addList(CourseAccessListDto courseAccessListDto) {
        System.out.println(courseAccessListDto.toString());
        for (String courseId: courseAccessListDto.getCourseId()) {
            Course course = courseRepository.findById(courseId).get();
            CourseAccess courseAccess = CourseAccess.builder()
                    .userId(courseAccessListDto.getUserId())
                    .course(course).build();
            courseAccessRepository.save(courseAccess);

            /*
                Add Course Progress
                + CurrentProgress 0 (Begin)
                + TotalAmountOfLecture = totalLectures of course
             */
            int totalAmountOfLecture = 0;
            for(Section section: course.getContent().getSections()) {
                totalAmountOfLecture += section.getLectures().size();
            }
            CourseDto courseDto = courseMapper.entityToDto(course);
            CourseProgressDto courseProgressDto = CourseProgressDto.builder()
                    .userId(courseAccessListDto.getUserId())
                    .course(courseDto)
                    .currentProgress(0)
                    .totalAmountOfLecture(totalAmountOfLecture)
                    .build();
            courseProgressService.create(courseProgressDto);
        }

        return ResponseMapper.toDataResponseSuccess("Success");
    }
}
