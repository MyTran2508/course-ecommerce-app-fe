package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataNotFoundException;
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
import com.programming.courseservice.domain.dto.SearchCourseDto;
import com.programming.courseservice.domain.mapper.CourseMapper;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.domain.persistent.entity.Image;
import com.programming.courseservice.domain.persistent.entity.Language;
import com.programming.courseservice.repository.CourseRepository;
import com.programming.courseservice.repository.LanguageRepository;
import com.programming.courseservice.repository.LevelRepository;
import com.programming.courseservice.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class CourseService extends BaseServiceImpl<Course, CourseDto> {
    private final CourseRepository courseRepository;
    private final LevelRepository levelRepository;
    private final LanguageRepository languageRepository;
    private final TopicRepository topicRepository;
    private final CourseMapper courseMapper;
    private final StorageService storageService;
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

        return ResponseMapper.toDataResponseSuccess(courseRepository.save(course));
    }

    @Transactional
    public DataResponse<String> uploadImages(MultipartFile[] files, String courseId, Integer defaultUrl) {
        String errorMsg = "";

        for(MultipartFile file: files) {
            if(!storageService.isFileImage(file)) {
                errorMsg += file.getOriginalFilename() + ",";
            }
        }

        if(!errorMsg.isEmpty()) {
            errorMsg = errorMsg.substring(0, errorMsg.length() - 1);
            return ResponseMapper.toDataResponse("List of images is error: " + errorMsg, StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
        }

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            throw new DataNotFoundException(courseId + " doesn't exist in DB");
        }

        Course course = courseOptional.get();

        Set<Image> images = new HashSet<>();
        int idx = 0;
        for (MultipartFile file: files) {
            String filePath = storageService.uploadImageToFileSystem(file);
            Image image = Image.builder().url(filePath)
                    .isDefaultImage(defaultUrl == idx)
                    .course(course)
                    .build();
            idx++;
            images.add(image);
        }

        if(course.getImages().size() > 0) {
            for (Image image: course.getImages()) {
                storageService.deleteFileFromSystem(image.getUrl());
            }
        }

        course.setImagesAll(images);
        System.out.println(course);
        courseRepository.save(course);
        return ResponseMapper.toDataResponseSuccess("Upload Images Successfully!");
    }

    public List<byte[]> getImages(String courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()) {
            throw new DataNotFoundException(courseId + " doesn't exists in DB");
        }
        Course course = optionalCourse.get();
        List<byte[]> images = new ArrayList<>();
        for (Image image: course.getImages()) {
            File file = new File(image.getUrl());
            try {
                byte[] imageBytes = Files.readAllBytes(file.toPath());
                images.add(imageBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return images;
    }
}
