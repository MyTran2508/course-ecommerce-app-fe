package com.programming.courseservice.service;

import com.main.progamming.common.error.exception.DataAlreadyExistException;
import com.main.progamming.common.error.exception.DataConflictException;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.programming.courseservice.domain.dto.UserAnswerDto;
import com.programming.courseservice.domain.mapper.UserAnswerMapper;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.domain.persistent.entity.UserAnswer;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import com.programming.courseservice.repository.ExQuizRepository;
import com.programming.courseservice.repository.UserAnswerRepository;
import com.programming.courseservice.repository.UserQuizRepository;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAnswerService {
    private final UserAnswerRepository userAnswerRepository;

    private final UserQuizRepository userQuizRepository;

    private final ExQuizRepository exQuizRepository;

    private final UserAnswerMapper userAnswerMapper;

    @SuppressWarnings("unchecked")
    @Transactional
    public DataResponse<String> upsertUserAnswer(List<UserAnswerDto> userAnswerDtoList, String userQuizId, Boolean isSubmit) {
        Optional<UserQuiz> userQuizOptional = userQuizRepository.findById(userQuizId);
        if (userQuizOptional.isEmpty()) {
            throw new ResourceNotFoundException(CourseConstrant.ErrorConstrant.ID_NOT_FOUND);
        }

        UserQuiz userQuiz = userQuizOptional.get();
        long currentTime = System.currentTimeMillis();
        if (currentTime > userQuiz.getStartTime() + userQuiz.getLimitTime()) {
            throw new DataConflictException(CourseConstrant.ErrorConstrant.TIME_OUT);
        }

        if (userQuiz.getIsCompleted()) {
            throw new DataAlreadyExistException(CourseConstrant.ErrorConstrant.QUIZ_HAS_ENDED);
        }

        List<UserAnswer> listSave = new LinkedList<>();
        for (UserAnswerDto userAnswerDto : userAnswerDtoList) {
            UserAnswer userAnswer = userAnswerMapper.dtoToEntity(userAnswerDto);
            userAnswer.setUserQuiz(userQuiz);
            listSave.add(userAnswer);
        }

        try {
            userAnswerRepository.saveAllAndFlush(listSave);
            if (isSubmit) {
                evaluateQuiz(userQuizId);
            }
            return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.UPSERT_SUCCESS);
        } catch (Exception ex) {
            throw new DataConflictException(CourseConstrant.ErrorConstrant.SAVE_USER_ANSWER_FAIL);
        }
    }

    public void evaluateQuiz(String userQuizId) {
        UserQuiz userQuiz = userQuizRepository.findById(userQuizId).get();
        ExQuiz exQuiz = exQuizRepository.findById(userQuiz.getExQuizId()).get();

        int correctAnswerCount = 0;

        for (UserAnswer userAnswer : userQuiz.getUserAnswers()) {
            if (userAnswer.getCurrentAnswer().equals(userAnswer.getQuestion().getRightAnswer())) {
                correctAnswerCount++;
            }
        }

        userQuiz.setCorrectAnswerCount((short) correctAnswerCount);
        double rawScore = (double) correctAnswerCount / exQuiz.getQuestions().size() * 10;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double roundedScore = Double.parseDouble(decimalFormat.format(rawScore));
        userQuiz.setScore(roundedScore);
        userQuiz.setIsCompleted(true);

        userQuizRepository.save(userQuiz);
    }
}
