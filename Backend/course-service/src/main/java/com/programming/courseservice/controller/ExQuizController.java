package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.ApiResources;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.ExQuizDto;
import com.programming.courseservice.domain.dto.UserLogDto;
import com.programming.courseservice.domain.mapper.ExQuizMapper;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.domain.persistent.enumrate.ActionName;
import com.programming.courseservice.domain.persistent.enumrate.ActionObject;
import com.programming.courseservice.repository.ExQuizRepository;
import com.programming.courseservice.repository.LectureRepository;
import com.programming.courseservice.service.ExQuizService;
import com.programming.courseservice.service.UserLogService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import com.programming.courseservice.utilities.communication.UserApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/ex-quiz")
@RequiredArgsConstructor
public class ExQuizController extends BaseApiImpl<ExQuiz, ExQuizDto> {

    private final ExQuizService exQuizService;

    private final ExQuizRepository exQuizRepository;

    private final LectureRepository lectureRepository;

    private final ExQuizMapper exQuizMapper;

    private final UserLogService userLogService;

    private final UserApi userApi;

    @Override
    protected BaseService<ExQuiz, ExQuizDto> getBaseService() {
        return exQuizService;
    }

    @Override
    public ListResponse<ExQuizDto> searchByKeyword(SearchKeywordDto searchKeywordDto) {
        return super.searchByKeyword(searchKeywordDto);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<ExQuizDto> getById(String id) {
        return super.getById(id);
    }

    @PostMapping(ApiResources.ADD + "/{lectureId}")
    @ShowOpenAPI
    public DataResponse<String> add(@PathVariable("lectureId") String lectureId, @RequestBody ExQuizDto exQuizDto) {

        DataResponse<String> response = exQuizService.create(lectureId, exQuizDto);

        String stResult = response.getData();
        String exQuizId = stResult.split(": ")[1].trim();
        ExQuiz exQuiz = exQuizRepository.findById(exQuizId).orElse(null);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Lecture ID: " + lectureId + "; Exercise Quiz ID: " + exQuizId)
                .actionObject(ActionObject.EX_QUIZ)
                .actionName(ActionName.CREATE)
                .description(userLogService.writePersistLog(ExQuiz.class, exQuiz, true, 0))
                .build();
        System.out.println(userLogDto);
        userApi.addLog(userLogDto);

        return response;
    }

    @Override
    @ShowOpenAPI
    public DataResponse<ExQuizDto> update(ExQuizDto objectDTO, String id) {
        ExQuiz savedExQuiz = exQuizRepository.findById(id).orElse(null);
        if (savedExQuiz == null) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }

        Lecture savedLecture = lectureRepository.findByExQuiz(savedExQuiz);
        String lectureId = "";
        if (savedLecture != null) {
            lectureId = savedLecture.getId();
        }
        ExQuiz oldExQuizClone = SerializationUtils.clone(savedExQuiz);
        System.out.println("prefix user: " + oldExQuizClone);

        DataResponse<ExQuizDto> response = super.update(objectDTO, id);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Lecture ID: " + lectureId + "; ExQuiz ID: " + id)
                .actionObject(ActionObject.EX_QUIZ)
                .actionName(ActionName.UPDATE)
                .description(userLogService.writeUpdateLog(ExQuiz.class, oldExQuizClone, exQuizMapper.dtoToEntity(response.getData()), true, 0))
                .build();

        userApi.addLog(userLogDto);

        return response;
    }
}
