package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchConditionDto;
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
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.*;
import com.programming.courseservice.domain.mapper.CourseIssueReportMapper;
import com.programming.courseservice.domain.mapper.CourseMapper;
import com.programming.courseservice.domain.persistent.entity.*;
import com.programming.courseservice.domain.persistent.enumrate.*;
import com.programming.courseservice.repository.*;
import com.programming.courseservice.utilities.EnumUtils;
import com.programming.courseservice.utilities.TimeUtils;
import com.programming.courseservice.utilities.communication.UserApi;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import com.programming.courseservice.utilities.constant.UserConstant;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.*;
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

    private final StorageService storageService;

    private final LanguageRepository languageRepository;

    private final TopicRepository topicRepository;

    private final LevelRepository levelRepository;

    private final CourseIssueReportMapper courseIssueReportMapper;

    private final CourseReviewRepository courseReviewRepository;

    private final UserLogService userLogService;

    private final UserApi userApi;

    private final EntityManager entityManager;

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

        List<String> nameList = new ArrayList<>();
        List<String> authorNameList = new ArrayList<>();
        List<String> subTitleList = new ArrayList<>();

        Boolean isApproved = null;
        Boolean isAwaitingApproval = null;
        Boolean isCompletedContent = null;

        if (searchKeywordDto.getSearchChooseList() != null) {
            for (SearchConditionDto searchConditionDto: searchKeywordDto.getSearchChooseList()) {
                if (searchConditionDto.getKeywordType() == 0) {
                    nameList.add(searchConditionDto.getKeyword());
                } else if (searchConditionDto.getKeywordType() == 1) {
                    authorNameList.add(searchConditionDto.getKeyword());
                } else if (searchConditionDto.getKeywordType() == 2) {
                    subTitleList.add(searchConditionDto.getKeyword());
                } else if (searchConditionDto.getKeywordType() == 3) {
                    isApproved = Boolean.parseBoolean(searchConditionDto.getKeyword());
                } else if (searchConditionDto.getKeywordType() == 4) {
                    isAwaitingApproval = Boolean.parseBoolean(searchConditionDto.getKeyword());
                } else if (searchConditionDto.getKeywordType() == 5) {
                    isCompletedContent = Boolean.parseBoolean(searchConditionDto.getKeyword());
                }
            }
        }

        boolean isEmptySearchChooseList = nameList.isEmpty() && authorNameList.isEmpty() && subTitleList.isEmpty();

        Map<Integer, String> searchKeywordDtoMap = new HashMap<>() {{
            put(0, null);
            put(1, null);
            put(2, null);
        }};
        Boolean isNullAllSearchKeywordDto = true;

        if (searchKeywordDto.getSearchKeywordDtoList() != null) {
            for (SearchConditionDto searchConditionDto: searchKeywordDto.getSearchKeywordDtoList()) {
                if (searchConditionDto.getKeywordType() == 0) {
                    isNullAllSearchKeywordDto = false;
                    searchKeywordDtoMap.put(0, searchConditionDto.getKeyword());
                } else if (searchConditionDto.getKeywordType() == 1) {
                    isNullAllSearchKeywordDto = false;
                    searchKeywordDtoMap.put(1, searchConditionDto.getKeyword());
                } else if (searchConditionDto.getKeywordType() == 2) {
                    isNullAllSearchKeywordDto = false;
                    searchKeywordDtoMap.put(2, searchConditionDto.getKeyword());
                }
            }
        }

        return courseRepository.searchCourseOfAdmin(
                    isEmptySearchChooseList,
                    nameList,
                    authorNameList,
                    subTitleList,
                    searchKeywordDto.getPrice(),
                    searchKeywordDto.getMinPrice(),
                    searchKeywordDto.getMaxPrice(),
                    isNullAllSearchKeywordDto,
                    searchKeywordDtoMap.get(0),
                    searchKeywordDtoMap.get(1),
                    searchKeywordDtoMap.get(2),
                    isApproved,
                    isAwaitingApproval,
                    isCompletedContent,
                    pageable
                )
                .map(course -> courseMapper.entityToDto(course));
    }

    @Override
    protected List<CourseDto> getListSearchResults(String keyword) {
        return null;
    }

    @Override
    public ListResponse<CourseDto> getAll() {

        return ResponseMapper.toListResponseSuccess(courseRepository.getAllCourseIsApproved()
                .stream()
                .map(course -> courseMapper.entityToDto(course))
                .toList());
    }

    public ListResponse<List<CourseDto>> getNewestCourse(String topicId, int size) {
        Sort sortCourse = Sort.by(Sort.Direction.DESC, "created");
        Pageable pageable = PageRequest.of(0, size, sortCourse);

        topicId = topicId.equals("-1") ? null : topicId;
        List<CourseDto> courseDtos = courseRepository.getCourseByTopicId(topicId, pageable)
                .stream()
                .map((course -> courseMapper.entityToDto(course)))
                .collect(Collectors.toList());

        return ResponseMapper.toListResponseSuccess(courseDtos);
    }

    public ListResponse<List<CourseDto>> getPopularCourse(String topicId, Integer size) {
        Pageable pageable = PageRequest.of(0, size);

        topicId = topicId.equals("-1") ? null : topicId;
        List<CourseDto> courseDtos = courseRepository.findPopularCourses(topicId, pageable)
                .stream()
                .map((course -> courseMapper.entityToDto(course)))
                .collect(Collectors.toList());

        return ResponseMapper.toListResponseSuccess(courseDtos);
    }

    public ListResponse<CourseDto> getFiltedCourse(SearchCourseDto searchCourseDto) {
        Pageable pageable;
        if(searchCourseDto.getFilterSortBy() != null && searchCourseDto.getFilterSortBy() == FilterSortBy.NEWEST) {
            Sort sortCourse = Sort.by(Sort.Direction.DESC, "created");
            pageable = PageRequest.of(searchCourseDto.getPageIndex(), searchCourseDto.getPageSize(), sortCourse);
        } else {
            pageable = PageRequest.of(searchCourseDto.getPageIndex(), searchCourseDto.getPageSize());
        }

        List<String> levelIds = searchCourseDto.getLevelIds() == null || searchCourseDto.getLevelIds().isEmpty() ? levelRepository.findAll().stream().map(Level::getId).toList() : searchCourseDto.getLevelIds();
        List<String> languageIds = searchCourseDto.getLanguageIds() == null || searchCourseDto.getLanguageIds().isEmpty() ? languageRepository.findAll().stream().map(Language::getId).toList() : searchCourseDto.getLanguageIds();
        List<String> topicIds = searchCourseDto.getTopicIds() == null || searchCourseDto.getTopicIds().isEmpty() ? topicRepository.findAll().stream().map(Topic::getId).toList() : searchCourseDto.getTopicIds();
        Boolean isFree = searchCourseDto.getIsFree();
        VideoDuration videoDuration = searchCourseDto.getVideoDuration();
        RatingsLevel ratingsLevel = searchCourseDto.getRatingsLevel();
        Float minRatingValue = EnumUtils.getMinRating(ratingsLevel);

        SearchCourseKeywordDto searchCourseKeywordNameDto = searchCourseDto.getSearchCourseKeywordDtoList().stream()
                .filter(item -> item.getKeywordTypeSearchCourse() == 0)
                .findFirst().orElse(null);
        String keywordName = searchCourseKeywordNameDto == null ? null : searchCourseKeywordNameDto.getKeyword();

        List<String> keywordAuthors = searchCourseDto.getSearchCourseKeywordDtoList().stream()
                .filter(item -> item.getKeywordTypeSearchCourse() == 1)
                .map(SearchCourseKeywordDto::getKeyword)
                .collect(Collectors.toList());
        Boolean isEmptyKeywordAuthors = keywordAuthors.isEmpty();

        SearchCourseKeywordDto searchCourseKeywordSubTitleDto = searchCourseDto.getSearchCourseKeywordDtoList().stream()
                .filter(item -> item.getKeywordTypeSearchCourse() == 2)
                .findFirst().orElse(null);
        String keywordSubTitle = searchCourseKeywordSubTitleDto == null ? null : searchCourseKeywordSubTitleDto.getKeyword();

        System.out.println("keywordName: " + keywordName);
        System.out.println("keywordAuthors: " + keywordAuthors);
        System.out.println("keywordSubTitle: " + keywordSubTitle);
        System.out.println("isEmptyKeywordAuthors: " + isEmptyKeywordAuthors);

        Page<Course> courses = null;
        if(searchCourseDto.getFilterSortBy() != null && searchCourseDto.getFilterSortBy() == FilterSortBy.POPULAR) {
            courses = courseRepository.filterCoursePopular(levelIds, languageIds, topicIds, isFree, minRatingValue, keywordName,
                    isEmptyKeywordAuthors, keywordAuthors, keywordSubTitle, pageable);
        } else {
            System.out.println("normal search");
            courses = courseRepository.filterCourse(levelIds, languageIds, topicIds, isFree, minRatingValue, keywordName,
                    isEmptyKeywordAuthors, keywordAuthors, keywordSubTitle, pageable);
        }

        if (videoDuration != null) {
            Long minVideoDuration = TimeUtils.converHoursToMilliseconds(EnumUtils.getMinVideoDurationHour(videoDuration));
            Long maxVideoDuration = TimeUtils.converHoursToMilliseconds(EnumUtils.getMaxVideoDurationHour(videoDuration));

            List<CourseDto> courseDtos = courses.stream()
                    .filter(course -> {
                        Long totalDurationVideos = course.getContent().getSections().stream()
                                    .mapToLong(Section::getTotalDurationVideoLectures)
                                .sum();
                        if (totalDurationVideos <= minVideoDuration || totalDurationVideos > maxVideoDuration) {
                            return false;
                        }
                        return true;
                    })
                    .map(course -> courseMapper.entityToDto(course))
                    .toList();

            Page<CourseDto> courseDtoPage = new PageImpl<>(courseDtos, pageable, courseDtos.size());

            return ResponseMapper.toPagingResponseSuccess(courseDtoPage);
        }


        return ResponseMapper.toPagingResponseSuccess(courses.map(course -> courseMapper.entityToDto(course)));
    }

    @Override
    public DataResponse<CourseDto> update(String id, CourseDto dto) {
        courseRepository.updateCourse(id, dto.getLevel().getId(), dto.getTopic().getId(), dto.getLanguage().getId());

        Optional<Course> optionalCourse = courseRepository.findById(id);
        if(optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException(CourseConstrant.ErrorConstrant.ID_NOT_FOUND);
        }

        Course course = optionalCourse.get();
        courseMapper.dtoToEntity(dto, course);
        course.setId(id);

        return ResponseMapper.toDataResponseSuccess(courseMapper.entityToDto(courseRepository.save(course)));
//        Optional<Course> optionalCourse = courseRepository.findById(id);
//        if(optionalCourse.isEmpty()) {
//            throw new ResourceNotFoundException(CourseConstrant.ErrorConstrant.ID_NOT_FOUND);
//        }
//
//        Course course = optionalCourse.get();
//        entityManager.detach(course);
//
//        courseMapper.dtoToEntity(dto, course);
//        course.setId(id);
//        Level level = levelRepository.findById(dto.getLevel().getId()).orElse(null);
//        Language language = languageRepository.findById(dto.getLanguage().getId()).orElse(null);
//        Topic topic = topicRepository.findById(dto.getTopic().getId()).orElse(null);
//        course.setLevel(level);
//        course.setLanguage(language);
//        course.setTopic(topic);
//
//
//        return ResponseMapper.toDataResponseSuccess(courseMapper.entityToDto(courseRepository.save(course)));
    }

    public DataResponse<String> uploadCourseImage(MultipartFile file) {
        return ResponseMapper.toDataResponseSuccess(storageS3Service.uploadFile(CourseConstrant.S3Constrant.PATH_COURSE_IMAGE, file));
    }

    public DataResponse<String> uploadCourseVideo(MultipartFile file) {
        return ResponseMapper.toDataResponseSuccess(storageS3Service.uploadFile(CourseConstrant.S3Constrant.PATH_COURSE_VIDEO, file));
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

//    public ResponseEntity<?> loadFile(String path) {
//        byte[] file = storageService.loadImageFromFileSystem(path);
//        if(file == null) {
//            return ResponseEntity.ok("Error");
//        }
//        String imageBase64 = Base64.getEncoder().encodeToString(file);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(imageBase64);
//    }
//
//    public DataResponse<String> uploadCourseImage(MultipartFile file) {
//        String filePath = storageService.uploadImageToFileSystem(file);
//        if(filePath.isEmpty()) {
//            return ResponseMapper.toDataResponse("File error!!", StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
//        }
//        return ResponseMapper.toDataResponseSuccess(filePath);
//    }

//    public DataResponse<String> uploadCourseVideo(MultipartFile file) {
//        String filePath = storageService.uploadVideoToFileSystem(file);
//        if(filePath.isEmpty()) {
//            return ResponseMapper.toDataResponse("File error!!", StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
//        }
//        return ResponseMapper.toDataResponseSuccess(filePath);
//    }

    public ListResponse<CourseDto> getCourseAccessByUserId(String userId, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        Page<Course> courses = courseRepository.getCourseAccessByUserId(userId, pageable);
        Page<CourseDto> courseDtos = courses.map(course -> {
            CourseDto courseDto = courseMapper.entityToDto(course);
            CourseReview courseReview = courseReviewRepository.findCourseReviewsByUsernameAndCourseId(SystemUtil.getCurrentUsername(), course.getId());
            if (courseReview != null) {
                courseDto.setIsAlreadyReviewed(true);
            }
            return courseDto;
        });

        return ResponseMapper.toPagingResponseSuccess(courseDtos);
    }

    public DataResponse<String>  updateIsApproved(String id, boolean isApproved, CourseIssueReportDto courseIssueReportDto) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if(optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException(CourseConstrant.ErrorConstrant.ID_NOT_FOUND);
        }

        Course course = optionalCourse.get();
        if(isApproved) {
            if(course.getIsCompletedContent() && course.getIsAwaitingApproval()) {
                courseRepository.updateIsApproved(id, isApproved);

                // add logs
                // Add log
                UserLogDto userLogDto = UserLogDto.builder()
                        .userName(SystemUtil.getCurrentUsername())
                        .ip(SystemUtil.getUserIP())
                        .actionKey(id)
                        .actionObject(ActionObject.COURSE)
                        .actionName(ActionName.APPROVE_COURSE)
                        .description(UserConstant.PREFIX_USER_LOG + UserConstant.APPROVE_COURSE)
                        .build();
                userApi.addLog(userLogDto);
                return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.UPDATE_SUCCESS);
            } else {
                return ResponseMapper.toDataResponse(CourseConstrant.ErrorConstrant.CONTENT_NOT_COMPLETE, StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
            }
        } else {
            List<CourseIssueReport> courseIssueReports = course.getCourseIssueReports();

            CourseIssueReport courseIssueReport = courseIssueReportMapper.dtoToEntity(courseIssueReportDto);
            courseIssueReport.setCourse(course);
            courseIssueReports.add(courseIssueReport);
            courseIssueReports.forEach(System.out::println);
            course.setCourseIssueReports(courseIssueReports);
            course.setIsAwaitingApproval(false);

            // Add log
            UserLogDto userLogDto = UserLogDto.builder()
                    .userName(SystemUtil.getCurrentUsername())
                    .ip(SystemUtil.getUserIP())
                    .actionKey(id)
                    .actionObject(ActionObject.COURSE)
                    .actionName(ActionName.DISAPPROVE_COURSE)
                    .description(userLogService.writePersistLog(CourseIssueReport.class, courseIssueReport, true, 0))
                    .build();
            System.out.println(userLogDto);
            userApi.addLog(userLogDto);

            courseRepository.save(course);
            return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.UPDATE_SUCCESS);
        }
    }

    public DataResponse<String> updateAwaitingApproval(String id, boolean isAwaitingApproval) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if(optionalCourse.isEmpty()) {
            throw new ResourceNotFoundException(CourseConstrant.ErrorConstrant.ID_NOT_FOUND);
        }
        Course course = optionalCourse.get();
        if(isAwaitingApproval && !updateCompletedContent(course)) {
            return ResponseMapper.toDataResponse(CourseConstrant.ErrorConstrant.CONTENT_NOT_COMPLETE, StatusCode.DATA_NOT_MAP, StatusMessage.DATA_NOT_MAP);
        }
        course.setIsAwaitingApproval(isAwaitingApproval);
        courseRepository.save(course);
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey(id)
                .actionObject(ActionObject.COURSE)
                .actionName(ActionName.APPROVAL_REQUEST)
                .description(UserConstant.PREFIX_USER_LOG + UserConstant.APPROVAL_REQUEST)
                .build();
        System.out.println(userLogDto);
        userApi.addLog(userLogDto);
        return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.UPDATE_SUCCESS);
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

    public DataResponse<Integer> getTotalApprovedCourseByYearAndMonth(int targetYear, Integer targetMonth) {
        return ResponseMapper.toDataResponseSuccess(courseRepository.getTotalApprovedCourseByYearAndMonth(targetYear, targetMonth));
    }

    public DataResponse<List<SalesByTopicResponse>> getSalesByTopics(Integer targetYear) {
        List<Topic> topics = topicRepository.findAll();
        List<Object[]> salesByTopics = courseRepository.getMonthlySalesByTopics(targetYear);
        List<SalesByTopicResponse> salesByTopicResponses = new ArrayList<>();
        for (Topic topic: topics) {
            boolean f = false;
            for(Object[] salesByTopic: salesByTopics) {
                if(topic.getId().equals(salesByTopic[0])) {
                    f = true;
                    salesByTopicResponses.add(new SalesByTopicResponse(topic.getId(), topic.getName(), (Double) salesByTopic[1]));
                    break;
                }
            }
            if(!f) {
                salesByTopicResponses.add(new SalesByTopicResponse(topic.getId(), topic.getName(), 0.0));
            }
        }
        return ResponseMapper.toDataResponseSuccess(salesByTopicResponses.stream()
                .sorted(Comparator.comparingInt(SalesByTopicResponse::convertTopicIdAsInteger))
                .collect(Collectors.toList()));
    }

    @SuppressWarnings("unchecked")
    public DataResponse<List<SalesByTopicSamePeriodResponse>> getSalesSamePeriodByTopics(Integer targetYear) {
        List<Topic> topics = topicRepository.findAll();
        List<Object[]> salesTargetYearByTopics = courseRepository.getMonthlySalesByTopics(targetYear);
        List<Object[]> salesPreviousYearByTopics = courseRepository.getMonthlySalesByTopics(targetYear - 1);
        List<SalesByTopicSamePeriodResponse> salesByTopicSamePeriodResponses = new ArrayList<>();
        for (Topic topic: topics) {
            boolean f = false;
            SalesByTopicSamePeriodResponse salesByTopicSamePeriodResponse = new SalesByTopicSamePeriodResponse();
            salesByTopicSamePeriodResponse.setTopicId(topic.getId());
            salesByTopicSamePeriodResponse.setTopicName(topic.getName());
            for(Object[] salesTargetYearByTopic : salesTargetYearByTopics) {
                if(topic.getId().equals(salesTargetYearByTopic[0])) {
                    f = true;
                    salesByTopicSamePeriodResponse.setTargetYearTotal((Double) salesTargetYearByTopic[1]);
                    break;
                }
            }
            if(!f) {
                salesByTopicSamePeriodResponse.setTargetYearTotal(0.0);
            }
            f = false;

            for(Object[] salesPreviousYearByTopic : salesPreviousYearByTopics) {
                if(topic.getId().equals(salesPreviousYearByTopic[0])) {
                    f = true;
                    salesByTopicSamePeriodResponse.setPreviousYearTotal((Double) salesPreviousYearByTopic[1]);
                    break;
                }
            }
            if(!f) {
                salesByTopicSamePeriodResponse.setPreviousYearTotal(0.0);
            }

            salesByTopicSamePeriodResponses.add(salesByTopicSamePeriodResponse);
        }

        return ResponseMapper.toDataResponseSuccess(salesByTopicSamePeriodResponses.stream()
                .sorted(Comparator.comparingInt(SalesByTopicSamePeriodResponse::convertTopicIdAsInteger))
                .collect(Collectors.toList()));
    }

    public ListResponse<CourseDto> getCourseSearch(Integer typeSearch, String keyword) {

        List<CourseDto> courseDtoList =
                courseRepository.getCourseSearch(typeSearch, keyword)
                        .stream()
                        .map(course -> courseMapper.entityToDto(course))
                        .toList();

        return ResponseMapper.toListResponseSuccess(courseDtoList);
    }
}
