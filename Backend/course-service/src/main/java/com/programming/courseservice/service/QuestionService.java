package com.programming.courseservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.QuestionDto;
import com.programming.courseservice.domain.mapper.QuestionMapper;
import com.programming.courseservice.domain.persistent.entity.Question;
import com.programming.courseservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    private final QuestionMapper questionMapper;

    @SuppressWarnings("unchecked")
    public ListResponse<QuestionDto> getByExQuizId(String exQuizId, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        Page<QuestionDto> dataResult = questionRepository.findByExQuizId(exQuizId, pageable).map(
                question -> {
                    question.setAnswerExplanation(null);
                    question.setRightAnswer(null);
                    return questionMapper.entityToDto(question);
                }
        );

        return ResponseMapper.toPagingResponseSuccess(dataResult);
    }
}
