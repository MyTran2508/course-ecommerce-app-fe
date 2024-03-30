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
import com.main.progamming.common.util.CommonConstrant;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.QuestionDto;
import com.programming.courseservice.domain.mapper.QuestionMapper;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.domain.persistent.entity.Question;
import com.programming.courseservice.repository.ExQuizRepository;
import com.programming.courseservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService extends BaseServiceImpl<Question, QuestionDto> {

    private final QuestionRepository questionRepository;

    private final ExQuizRepository exQuizRepository;

    private final QuestionMapper questionMapper;

    private final UserQuizService userQuizService;

    @Override
    protected BaseRepository<Question> getBaseRepository() {
        return questionRepository;
    }

    @Override
    protected BaseMapper<Question, QuestionDto> getBaseMapper() {
        return questionMapper;
    }

    @Override
    protected Page<QuestionDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<QuestionDto> getListSearchResults(String keyword) {
        return null;
    }


    @SuppressWarnings("unchecked")
    public ListResponse<QuestionDto> getByExQuizId(String userId, String exQuizId, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        Boolean isCompleteQuiz = userQuizService.isCompleteQuiz(userId, exQuizId).getData();

        Page<QuestionDto> dataResult = null;
        // get questions by exQuizId
        if (!isCompleteQuiz) {
            dataResult = questionRepository.findByExQuizId(exQuizId, pageable).map(
                    question -> {
                        question.setAnswerExplanation(null);
                        question.setRightAnswer(null);
                        return questionMapper.entityToDto(question);
                    }
            );
        } else {
            dataResult = questionRepository.findByExQuizId(exQuizId, pageable).map(
                    question -> questionMapper.entityToDto(question));
        }

        return ResponseMapper.toPagingResponseSuccess(dataResult);
    }

    @SuppressWarnings("unchecked")
    public ListResponse<QuestionDto> getByExQuizIdManager(String exQuizId, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        // get questions by exQuizId
        Page<QuestionDto> dataResult = questionRepository.findByExQuizId(exQuizId, pageable).map(
                question -> questionMapper.entityToDto(question)
        );

        return ResponseMapper.toPagingResponseSuccess(dataResult);
    }

    // create a new question
    public DataResponse<String> add(String exQuizId, QuestionDto questionDto) {
        ExQuiz exQuiz = exQuizRepository.findById(exQuizId).orElse(null);

        if (exQuiz != null) {
            // convert dto to entity
            Question question = questionMapper.dtoToEntity(questionDto);

            // set exQuiz for question
            exQuiz.getQuestions().add(question);

            // save question
            exQuizRepository.save(exQuiz);

            // return success message
            return ResponseMapper.toDataResponseSuccess(CommonConstrant.INSERT_SUCCESS);
        } else {
            throw new ResourceNotFoundException(StatusMessage.DATA_NOT_FOUND);
        }
    }

    // update a list of questions
    public DataResponse<String> updateList(String exQuizId, List<QuestionDto> questionDtos) {
        ExQuiz exQuiz = exQuizRepository.findById(exQuizId).orElse(null);

        if (exQuiz != null) {

            List<Question> listQuestion = new ArrayList<>();
            for (QuestionDto questionDto: questionDtos) {
                // convert dto to entity
                Question question = questionMapper.dtoToEntity(questionDto);

                listQuestion.add(question);
            }

            exQuiz.setQuestions(listQuestion);
            // save exQuiz
            exQuizRepository.save(exQuiz);

            return ResponseMapper.toDataResponseSuccess(CommonConstrant.UPDATE_SUCCESS);
        } else {
            throw new ResourceNotFoundException(StatusMessage.DATA_NOT_FOUND);
        }
    }

    // add a list of questions
    public DataResponse<String> addList(String exQuizId, List<QuestionDto> questionDtos) {
        ExQuiz exQuiz = exQuizRepository.findById(exQuizId).orElse(null);

        if (exQuiz != null) {
            for (QuestionDto questionDto: questionDtos) {
                // convert dto to entity
                Question question = questionMapper.dtoToEntity(questionDto);

                // set exQuiz for question
                exQuiz.getQuestions().add(question);
            }

            exQuizRepository.save(exQuiz);

            return ResponseMapper.toDataResponseSuccess(CommonConstrant.INSERT_SUCCESS);
        } else {
            throw new ResourceNotFoundException(StatusMessage.DATA_NOT_FOUND);
        }
    }
}
