package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
    public DataResponse<UserQuizDto> getByUserIdAndExQuizId(String userId, String exQuizId) {

        UserQuiz userQuiz = userQuizRepository.findByUserIdAndExQuizId(userId, exQuizId);

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
        UserQuiz userQuiz = userQuizRepository.findByUserIdAndExQuizId(userId, exQuizId);

        if (userQuiz == null) {
            // throw exception if user quiz not found
            throw new ResourceNotFoundException(StatusMessage.DATA_NOT_FOUND);
        }

        // check if user quiz is complete
        boolean isComplete = userQuiz.getIsCompleted();

        // return success response
        return ResponseMapper.toDataResponseSuccess(isComplete);
    }

    @Override
    public DataResponse<String> create(UserQuizDto dto) {

        ExQuiz exQuiz = exQuizRepository.findById(dto.getExQuizId())
                .orElseThrow(() -> new ResourceNotFoundException(StatusMessage.DATA_NOT_FOUND));
        dto.setScore(0.0);
        UserQuiz userQuiz = userQuizMapper.dtoToEntity(dto);
        UserQuiz savedUserQuiz = userQuizRepository.save(userQuiz);

        List<UserAnswer> userAnswers = new ArrayList<>();
        for (Question question: exQuiz.getQuestions()) {
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setCurrentAnswer("");

            Question savedQuestion = new Question();
            savedQuestion.setId(question.getId());
            userAnswer.setQuestion(savedQuestion);

            userAnswer.setIsCorrect(false);
            userAnswer.setUserQuiz(savedUserQuiz);

            userAnswers.add(userAnswer);
        }

        userQuiz.setUserAnswers(userAnswers);
        userQuizRepository.save(userQuiz);
        return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.INSERT_SUCCES);
    }
}
