package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.ExQuizDto;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.service.ExQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public DataResponse<ExQuizDto> getById(String id) {
        return super.getById(id);
    }
}
