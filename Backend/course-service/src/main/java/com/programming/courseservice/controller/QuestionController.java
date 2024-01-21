package com.programming.courseservice.controller;

import com.main.progamming.common.response.ListResponse;
import com.programming.courseservice.domain.dto.QuestionDto;
import com.programming.courseservice.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/get-by-ex-quiz-id/{exQuizId}/{pageIndex}/{pageSize}")
    public ListResponse<QuestionDto> getByExQuizId(@PathVariable("exQuizId") String exQuizId, @PathVariable("pageIndex") Integer pageIndex,
            @PathVariable("pageSize") Integer pageSize) {

        return questionService.getByExQuizId(exQuizId, pageIndex, pageSize);
    }
}
