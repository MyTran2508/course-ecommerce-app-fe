package com.programming.courseservice.service;

import com.main.progamming.common.error.exception.DataConflictException;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.programming.courseservice.domain.dto.UserAnswerDto;
import com.programming.courseservice.domain.mapper.UserAnswerMapper;
import com.programming.courseservice.domain.persistent.entity.UserAnswer;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import com.programming.courseservice.repository.UserAnswerRepository;
import com.programming.courseservice.repository.UserQuizRepository;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;

    private final UserQuizRepository userQuizRepository;

    private final UserAnswerMapper userAnswerMapper;

    @SuppressWarnings("unchecked")
    public DataResponse<String> upsertUserAnswer(List<UserAnswerDto> userAnswerDtoList, String userQuizId) {
        Optional<UserQuiz> userQuizOptional = userQuizRepository.findById(userQuizId);
        if(userQuizOptional.isEmpty()) {
            throw new ResourceNotFoundException(CourseConstrant.ErrorConstrant.ID_NOT_FOUND);
        }

        UserQuiz userQuiz = userQuizOptional.get();
        List<UserAnswer> listSave = userAnswerDtoList.stream()
                .map(userAnswerDto -> {
                    UserAnswer userAnswer = userAnswerMapper.dtoToEntity(userAnswerDto);
                    userAnswer.setUserQuiz(userQuiz);
                    return userAnswer;
                })
                .toList();

        try {
            userAnswerRepository.saveAllAndFlush(listSave);
            return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.UPSERT_SUCCESS);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new DataConflictException(CourseConstrant.ErrorConstrant.SAVE_USER_ANSWER_FAIL);
        }
    }
}
