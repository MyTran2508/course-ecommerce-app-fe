package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.ApiResources;
import com.programming.courseservice.domain.dto.QuestionDto;
import com.programming.courseservice.domain.persistent.entity.Question;
import com.programming.courseservice.service.QuestionService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/question")
@RequiredArgsConstructor
public class QuestionController extends BaseApiImpl<Question, QuestionDto> {

    private final QuestionService questionService;

    @Override
    protected BaseService<Question, QuestionDto> getBaseService() {
        return questionService;
    }

    @GetMapping("/get-by-ex-quiz-id/{userId}/{exQuizId}/{pageIndex}/{pageSize}")
    @ShowOpenAPI
    public ListResponse<QuestionDto> getByExQuizId(@PathVariable("userId") String userId,
            @PathVariable("exQuizId") String exQuizId,
            @PathVariable("pageIndex") Integer pageIndex,
            @PathVariable("pageSize") Integer pageSize) {

        return questionService.getByExQuizId(userId, exQuizId, pageIndex, pageSize);
    }


    @GetMapping("/manager/get-by-ex-quiz-id/{exQuizId}/{pageIndex}/{pageSize}")
    @ShowOpenAPI
    public ListResponse<QuestionDto> getByExQuizIdManager(@PathVariable("exQuizId") String exQuizId, @PathVariable("pageIndex") Integer pageIndex,
            @PathVariable("pageSize") Integer pageSize) {

        return questionService.getByExQuizIdManager(exQuizId, pageIndex, pageSize);
    }

    @PostMapping(ApiResources.ADD + "/{exQuizId}")
    @ShowOpenAPI
    public DataResponse<String> add(@PathVariable("exQuizId") String exQuizId, @RequestBody QuestionDto questionDto) {

        return questionService.add(exQuizId, questionDto);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<QuestionDto> update(QuestionDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @PutMapping("update-list/{exQuizId}")
    @ShowOpenAPI
    public DataResponse<String> updateList(@PathVariable("exQuizId") String exQuizId, @RequestBody List<QuestionDto> questionDtos) {

        return questionService.updateList(exQuizId, questionDtos);
    }

    @PostMapping("add-list/{exQuizId}")
    @ShowOpenAPI
    public DataResponse<String> addList(@PathVariable("exQuizId") String exQuizId, @RequestBody List<QuestionDto> questionDtos) {
        return questionService.addList(exQuizId, questionDtos);
    }
}
