package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.ExQuizDto;
import com.programming.courseservice.domain.mapper.ExQuizMapper;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.repository.ExQuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExQuizService extends BaseServiceImpl<ExQuiz, ExQuizDto> {
    private final ExQuizRepository exQuizRepository;

    private final ExQuizMapper exQuizMapper;

    @Override
    protected BaseRepository<ExQuiz> getBaseRepository() {
        return exQuizRepository;
    }

    @Override
    protected BaseMapper<ExQuiz, ExQuizDto> getBaseMapper() {
        return exQuizMapper;
    }

    @Override
    protected Page<ExQuizDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<ExQuizDto> getListSearchResults(String keyword) {
        return null;
    }

    @Override
    public DataResponse<ExQuizDto> getById(String id) {
        DataResponse<ExQuizDto> response = super.getById(id);
        response.getData().setQuestions(null);

        return response;
    }
}
