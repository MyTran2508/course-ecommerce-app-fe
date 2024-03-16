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
import java.util.*;
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
        // Check if the userQuizId not exists
        if (userQuizOptional.isEmpty()) {
            throw new ResourceNotFoundException(CourseConstrant.ErrorConstrant.ID_NOT_FOUND);
        }

        UserQuiz userQuiz = userQuizOptional.get();
        long currentTime = System.currentTimeMillis();
        // Check if the quiz has ended
        if (currentTime > userQuiz.getStartTime() + userQuiz.getLimitTime()) {
            throw new DataConflictException(CourseConstrant.ErrorConstrant.TIME_OUT);
        }

        // check if the quiz has been completed
        if (userQuiz.getIsCompleted()) {
            throw new DataAlreadyExistException(CourseConstrant.ErrorConstrant.QUIZ_HAS_ENDED);
        }

        List<UserAnswer> listSave = new ArrayList<>();
        for (UserAnswerDto userAnswerDto : userAnswerDtoList) {
            UserAnswer userAnswer = userAnswerMapper.dtoToEntity(userAnswerDto);
            userAnswer.setUserQuiz(userQuiz);
            listSave.add(userAnswer);
        }

        userQuiz.setUserAnswers(listSave);
        try {
            userQuizRepository.save(userQuiz);
            if (isSubmit) {
                // Evaluate the quiz
                evaluateQuiz(userQuizId);
            }
            return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.UPSERT_SUCCESS);
        } catch (Exception ex) {
            throw new DataConflictException(CourseConstrant.ErrorConstrant.SAVE_USER_ANSWER_FAIL);
        }
    }

    // Evaluate the quiz
    public void evaluateQuiz(String userQuizId) {
        // Get userQuiz by userQuizId
        UserQuiz userQuiz = userQuizRepository.findById(userQuizId).get();

        // Get exQuiz by exQuizId
        ExQuiz exQuiz = exQuizRepository.findById(userQuiz.getExQuizId()).get();

        int correctAnswerCount = 0;

        // Count the correct answer
        for (UserAnswer userAnswer : userQuiz.getUserAnswers()) {
            if (userAnswer.getCurrentAnswer().equals(userAnswer.getQuestion().getRightAnswer())) {
                correctAnswerCount++;
                userAnswer.setIsCorrect(true);
            } else {
                userAnswer.setIsCorrect(false);
            }
        }

        userAnswerRepository.saveAll(userQuiz.getUserAnswers());

        // Calculate the score
        double rawScore = (double) correctAnswerCount / exQuiz.getQuestions().size() * 10;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double roundedScore = Double.parseDouble(decimalFormat.format(rawScore));

        // save the score and set the quiz as completed
        userQuiz.setCorrectAnswerCount((short) correctAnswerCount);
        userQuiz.setScore(roundedScore);
        userQuiz.setIsCompleted(true);
        userQuizRepository.save(userQuiz);
    }
}
