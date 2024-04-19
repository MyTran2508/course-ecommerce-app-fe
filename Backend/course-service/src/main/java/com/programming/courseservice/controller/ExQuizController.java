package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.ApiResources;
import com.programming.courseservice.domain.dto.ExQuizDto;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.service.ExQuizService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses/ex-quiz")
@RequiredArgsConstructor
public class ExQuizController extends BaseApiImpl<ExQuiz, ExQuizDto> {
    private final ExQuizService exQuizService;

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

        return exQuizService.create(lectureId, exQuizDto);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<ExQuizDto> update(ExQuizDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }
}
