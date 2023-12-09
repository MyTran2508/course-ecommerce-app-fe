package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.CourseDto;
import com.programming.courseservice.domain.dto.CourseIssueReportDto;
import com.programming.courseservice.domain.dto.SearchCourseDto;
import com.programming.courseservice.domain.mapper.CourseIssueReportMapper;
import com.programming.courseservice.domain.mapper.CourseMapper;
import com.programming.courseservice.domain.persistent.entity.*;
import com.programming.courseservice.repository.CourseRepository;
import com.programming.courseservice.repository.LanguageRepository;
import com.programming.courseservice.repository.LevelRepository;
import com.programming.courseservice.repository.TopicRepository;
import com.programming.courseservice.utilities.constant.S3Constrant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService extends BaseServiceImpl<Course, CourseDto> {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final StorageS3Service storageS3Service;
    private final LanguageRepository languageRepository;
    private final TopicRepository topicRepository;
    private final LevelRepository levelRepository;
    private final CourseIssueReportMapper courseIssueReportMapper;
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
        String name = searchKeywordDto.getKeyword().get(0) == null ? null : searchKeywordDto.getKeyword().get(0).trim();
        Boolean isApproved = searchKeywordDto.getKeyword().get(1) == null ? null : Boolean.valueOf(searchKeywordDto.getKeyword().get(1).trim());
        Boolean isAwaitingApproval = searchKeywordDto.getKeyword().get(2) == null ? null : Boolean.valueOf(searchKeywordDto.getKeyword().get(2).trim());
        return courseRepository.searchCourseOfAdmin(name, isApproved, isAwaitingApproval, pageable)
                .map(course -> courseMapper.entityToDto(course));
    }

    @Override
    protected List<CourseDto> getListSearchResults(String keyword) {
        return null;
    }

    @Override
    public ListResponse<CourseDto> getAll() {
        return super.getAll();
    }

    public ListResponse<List<CourseDto>> getNewestCourse(String topicId, int size) {
        Sort sortCourse = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(0, size, sortCourse);

        List<CourseDto> courseDtos = courseRepository.getCourseByTopicId(topicId, pageable)
                .stream()
                .map((course -> courseMapper.entityToDto(course)))
                .collect(Collectors.toList());

        return ResponseMapper.toListResponseSuccess(courseDtos);
    }

    public ListResponse<List<CourseDto>> getPopularCourse(String topicId, Integer size) {
        Pageable pageable = PageRequest.of(0, size);

        List<CourseDto> courseDtos = courseRepository.findPopularCourses(topicId, pageable)
                .stream()
                .map((course -> courseMapper.entityToDto(course)))
                .collect(Collectors.toList());

        return ResponseMapper.toListResponseSuccess(courseDtos);
    }

    public ListResponse<CourseDto> getFiltedCourse(SearchCourseDto searchCourseDto) {
        Pageable pageable = PageRequest.of(searchCourseDto.getPageIndex(), searchCourseDto.getPageSize());
        List<String> levelIds = searchCourseDto.getLevelIds() == null || searchCourseDto.getLevelIds().isEmpty() ? levelRepository.findAll().stream().map(Level::getId).toList() : searchCourseDto.getLevelIds();
        List<String> languageIds = searchCourseDto.getLanguageIds() == null || searchCourseDto.getLanguageIds().isEmpty() ? languageRepository.findAll().stream().map(Language::getId).toList() : searchCourseDto.getLanguageIds();
        List<String> topicIds = searchCourseDto.getTopicIds() == null || searchCourseDto.getTopicIds().isEmpty() ? topicRepository.findAll().stream().map(Topic::getId).toList() : searchCourseDto.getTopicIds();
        String keyword = searchCourseDto.getKeyword();

        Page<Course> courses = courseRepository.filterCourse(levelIds,
                languageIds, topicIds, keyword, pageable);
        Page<CourseDto> courseDtos = courses.map(course -> courseMapper.entityToDto(course));
        return ResponseMapper.toPagingResponseSuccess(courseDtos);

    }

    @Override
    public DataResponse<CourseDto> update(String id, CourseDto dto) {
        courseRepository.updateCourse(id, dto.getLevel().getId(), dto.getTopic().getId(), dto.getLanguage().getId());
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if(optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }
        Course course = optionalCourse.get();
        courseMapper.dtoToEntity(dto, course);
        course.setId(id);

        return ResponseMapper.toDataResponseSuccess(courseMapper.entityToDto(courseRepository.save(course)));
    }

    public DataResponse<String> uploadCourseImage(MultipartFile file) {
        return ResponseMapper.toDataResponseSuccess(storageS3Service.uploadFile(S3Constrant.PATH_COURSE_IMAGE, file));
    }

    public DataResponse<String> uploadCourseVideo(MultipartFile file) {
        return ResponseMapper.toDataResponseSuccess(storageS3Service.uploadFile(S3Constrant.PATH_COURSE_VIDEO, file));
    }

    public ResponseEntity<ByteArrayResource> loadFile(String path) {
        byte[] data = storageS3Service.downloadFile(path);
        byte[] dataBase64 = Base64.getEncoder().encode(data);
        ByteArrayResource resource = new ByteArrayResource(dataBase64);
        return ResponseEntity.ok()
                .contentLength(dataBase64.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; fileName=\"" + path + "\"")
                .body(resource);
    }

    public ListResponse<CourseDto> getCourseAccessByUserId(String userId, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<Course> courses = courseRepository.getCourseAccessByUserId(userId, pageable);
        Page<CourseDto> courseDtos = courses.map(course -> courseMapper.entityToDto(course));
        return ResponseMapper.toPagingResponseSuccess(courseDtos);
    }

    public DataResponse<String> updateIsApproved(String id, boolean isApproved, CourseIssueReportDto courseIssueReportDto) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if(optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException("Course doesn't exist");
        }
        Course course = optionalCourse.get();
        if(isApproved) {
            if(course.getIsCompletedContent() && course.getIsAwaitingApproval()) {
                courseRepository.updateIsApproved(id, isApproved);
                return ResponseMapper.toDataResponseSuccess("Update succcessful");
            } else {
                return ResponseMapper.toDataResponse("Content is in incompleted", StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
            }
        } else {
            List<CourseIssueReport> courseIssueReports = course.getCourseIssueReports();
            CourseIssueReport courseIssueReport = courseIssueReportMapper.dtoToEntity(courseIssueReportDto);
            courseIssueReport.setCourse(course);
            courseIssueReports.add(courseIssueReport);
            courseIssueReports.forEach(System.out::println);
            course.setCourseIssueReports(courseIssueReports);
            courseRepository.save(course);
            return ResponseMapper.toDataResponseSuccess("Update succcessful");
        }
    }

    public DataResponse<String> updateAwaitingApproval(String id, boolean isAwaitingApproval) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if(optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException("Course doesn't exist");
        }
        Course course = optionalCourse.get();
        if(isAwaitingApproval && !updateCompletedContent(course)) {
            return ResponseMapper.toDataResponse("Course content is incomplete", StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
        }
        course.setIsAwaitingApproval(isAwaitingApproval);
        courseRepository.save(course);
        return ResponseMapper.toDataResponseSuccess("Update succcessful");
    }

    public boolean updateCompletedContent(Course course) {
        if(!course.getIsCompletedContent()) {
            if(course.getContent() != null
                    && course.getContent().getDescription() != null
                    && course.getContent().getSections().size() > 0
                    && course.getContent().getSections().get(0).getLectures().size() > 0) {
                course.setIsCompletedContent(true);
                courseRepository.save(course);
            } else {
                return false;
            }
        }
        return true;
    }
}
