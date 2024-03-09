package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.ExQuizDto;
import com.programming.courseservice.domain.mapper.ExQuizMapper;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.repository.ExQuizRepository;
import com.programming.courseservice.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExQuizService extends BaseServiceImpl<ExQuiz, ExQuizDto> {
    private final ExQuizRepository exQuizRepository;

    private final ExQuizMapper exQuizMapper;

    private final LectureRepository lectureRepository;

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

    /**
     * Create ex-quiz by lecture id
     */
    public DataResponse<String> create(String lectureId, ExQuizDto dto) {
        // find lecture by id
        Lecture lecture = lectureRepository.findById(lectureId).orElse(null);

        if (lecture != null) {
            // convert dto to entity
            ExQuiz exQuiz = exQuizMapper.dtoToEntity(dto);
            lecture.setExQuiz(exQuiz);

            // save lecture
            lectureRepository.save(lecture);

            // return success response
            return ResponseMapper.toDataResponseSuccess(StatusMessage.REQUEST_SUCCESS);
        } else {
            // throw exception if lecture not found
            throw new DataNotFoundException(StatusMessage.DATA_NOT_FOUND);
        }
    }
}
