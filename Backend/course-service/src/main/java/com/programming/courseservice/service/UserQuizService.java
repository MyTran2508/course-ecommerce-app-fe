package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.QuestionDto;
import com.programming.courseservice.domain.dto.UserAnswerDto;
import com.programming.courseservice.domain.dto.UserQuizDto;
import com.programming.courseservice.domain.mapper.UserQuizMapper;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.domain.persistent.entity.Question;
import com.programming.courseservice.domain.persistent.entity.UserAnswer;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import com.programming.courseservice.repository.ExQuizRepository;
import com.programming.courseservice.repository.UserQuizRepository;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserQuizService extends BaseServiceImpl<UserQuiz, UserQuizDto> {

    private final UserQuizRepository userQuizRepository;

    private final UserQuizMapper userQuizMapper;

    private final ExQuizRepository exQuizRepository;

    @Override
    protected BaseRepository<UserQuiz> getBaseRepository() {
        return userQuizRepository;
    }

    @Override
    protected BaseMapper<UserQuiz, UserQuizDto> getBaseMapper() {
        return userQuizMapper;
    }

    @Override
    protected Page<UserQuizDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<UserQuizDto> getListSearchResults(String keyword) {
        return null;
    }

    // get user quiz by user id and ex quiz id
    public DataResponse<UserQuizDto> getByUserIdAndExQuizIdAndAttemptNumber(String userId, String exQuizId, Integer attemptNumber) {

        UserQuiz userQuiz = userQuizRepository.findByUserIdAndExQuizIdAndAttemptNumber(userId, exQuizId, attemptNumber);

        if (userQuiz == null) {
            // throw exception if user quiz not found
            throw new ResourceNotFoundException(StatusMessage.DATA_NOT_FOUND);
        }

        List<UserAnswer> sortedUserAnswers = userQuiz.getUserAnswers().stream()
                .sorted(Comparator.comparing((UserAnswer userAnswer) -> userAnswer.getQuestion().getId()))
                .toList();

        userQuiz.setUserAnswers(sortedUserAnswers);

        // convert entity to dto
        UserQuizDto userQuizDto = userQuizMapper.entityToDto(userQuiz);

        // return success response
        return ResponseMapper.toDataResponseSuccess(userQuizDto);
    }

    public DataResponse<Boolean> isCompleteQuiz(String userId, String exQuizId) {
        List<UserQuiz> userQuizzs = userQuizRepository.findByUserIdAndExQuizIdOrderByAttemptNumberAsc(userId, exQuizId);

        ExQuiz exQuiz = exQuizRepository.findById(exQuizId)
                .orElseThrow(() -> new ResourceNotFoundException(StatusMessage.DATA_NOT_FOUND));

        boolean isCompleted = false;
        for (UserQuiz userQuiz: userQuizzs) {
            if (userQuiz.getIsCompleted()) {
                isCompleted = true;
                break;
            }
        }

        return ResponseMapper.toDataResponseSuccess(isCompleted);
    }

    @Override
    @Transactional
    public DataResponse<String> create(UserQuizDto dto) {

        ExQuiz exQuiz = exQuizRepository.findById(dto.getExQuizId())
                .orElseThrow(() -> new ResourceNotFoundException(StatusMessage.DATA_NOT_FOUND));

        // get next value of attempt number for user quiz
        Integer attemptNumber = userQuizRepository.getNextAttemptNumber(dto.getUserId(), dto.getExQuizId());
        if (attemptNumber != null && attemptNumber >= exQuiz.getMaxAttemptNumber()) {
            // throw exception if user quiz is over limit attempt number
            throw new ResourceNotFoundException(CourseConstrant.ErrorConstrant.OVER_LIMIT_ATTEMPT_NUMBER);
        }

        // set value for user quiz
        dto.setScore(0.0);
        dto.setAttemptNumber(attemptNumber == null ? 1 : attemptNumber + 1);

        // convert entity and save user quiz
        UserQuiz userQuiz = userQuizMapper.dtoToEntity(dto);
        UserQuiz savedUserQuiz = userQuizRepository.save(userQuiz);

        // create user answer for each question in ex quiz
        List<UserAnswer> userAnswers = new ArrayList<>();
        for (Question question: exQuiz.getQuestions()) {
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setCurrentAnswer("");
            userAnswer.setQuestion(question);

            userAnswer.setIsCorrect(false);
            userAnswer.setUserQuiz(savedUserQuiz);

            userAnswer.setAttemptNumber(attemptNumber == null ? 1 : attemptNumber + 1);
            userAnswers.add(userAnswer);
        }

        // save user answers
        userQuiz.setUserAnswers(userAnswers);
        userQuizRepository.save(userQuiz);

        return ResponseMapper.toDataResponseSuccess(userQuizMapper.entityToDto(userQuiz));
    }

    public ListResponse<UserQuizDto> getByUserIdAndExQuizId(String userId, String exQuizId) {
        List<UserQuiz> userQuizzes = userQuizRepository.findByUserIdAndExQuizIdOrderByAttemptNumberAsc(userId, exQuizId);

        List<UserQuizDto> userQuizDtos = new ArrayList<>();
        for (UserQuiz userQuiz: userQuizzes) {
            userQuiz.setUserAnswers(null);

            UserQuizDto userQuizDto = userQuizMapper.entityToDto(userQuiz);
            userQuizDtos.add(userQuizDto);
        }

        return ResponseMapper.toListResponseSuccess(userQuizDtos);
    }
}
