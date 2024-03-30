package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.UserQuizDto;
import com.programming.courseservice.domain.mapper.UserQuizMapper;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import com.programming.courseservice.repository.UserQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQuizService extends BaseServiceImpl<UserQuiz, UserQuizDto> {

    private final UserQuizRepository userQuizRepository;

    private final UserQuizMapper userQuizMapper;

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
}
