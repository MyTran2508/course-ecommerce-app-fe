package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
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
}
