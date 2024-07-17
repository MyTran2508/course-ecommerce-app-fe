package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataConflictException;
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
import com.programming.courseservice.domain.persistent.enumrate.QuizType;
import com.programming.courseservice.repository.ExQuizRepository;
import com.programming.courseservice.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Question> questionList = exQuiz.getQuestions();

        if (exQuiz != null) {
            // convert dto to entity
            Question question = questionMapper.dtoToEntity(questionDto);

            for (Question q: questionList) {
                String savedTitle = q.getTitle().replaceAll("\\s+", "");
                String newTitle = question.getTitle().replaceAll("\\s+", "");
                if (savedTitle.equals(newTitle)) {
                    throw new DataConflictException(StatusMessage.DATA_CONFLICT);
                }
            }

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

    public DataResponse<String> addExcel(String exQuizId, MultipartFile file) {
        try {
            ExQuiz exQuiz = exQuizRepository.findById(exQuizId).orElse(null);
            List<Question> savedQuestion = exQuiz.getQuestions();
            List<Question> mergedList = new ArrayList<>(savedQuestion);
            List<Question> questionList = new ArrayList<>();
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Lấy header row
            Row headerRow = rows.next();
            List<String> headers = new ArrayList<>();
            headerRow.forEach(cell -> headers.add(cell.getStringCellValue()));
            int index = 1;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Question question = new Question();

                for (int i = 0; i < headers.size(); i++) {
                    System.out.println("i: " + i);
                    Cell currentCell = currentRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (headers.get(i)) {
                        case "ordinalNumber":
                            question.setOrdinalNumber((int) currentCell.getNumericCellValue());
                            break;
                        case "title":
                            question.setTitle(currentCell.getStringCellValue());
                            break;
                        case "options":
                            question.setOptions(
                                    (question.getOptions() == null ? "" : question.getOptions() + "\n") + currentCell.getStringCellValue());
                            break;
                        case "rightAnswer":
                            question.setRightAnswer(currentCell.getStringCellValue());
                            break;
                        case "answerExplanation":
                            question.setAnswerExplanation(currentCell.getStringCellValue());
                            break;
                        case "quizType":
                            question.setQuizType(QuizType.valueOf(currentCell.getStringCellValue()));
                        default:
                            break;
                    }
                }

                // check validate
                if(question.getOrdinalNumber() == null) {
                    throw new DataConflictException("Row " + index + ": " + "Ordinal Number is required");
                }
                if(question.getTitle() == null || question.getTitle().isEmpty()) {
                    throw new DataConflictException("Row " + index + ": " + "Title is required");
                }
                if(question.getOptions() == null || question.getOptions().isEmpty()) {
                    throw new DataConflictException("Row " + index + ": " + "Options is required");
                }
                if (question.getOptions().split("\n").length < 2) {
                    throw new DataConflictException("Row " + index + ": " + "Options must have at least 2 values");
                }
                if(question.getRightAnswer() == null || question.getRightAnswer().isEmpty()) {
                    throw new DataConflictException("Row " + index + ": " + "Right Answer is required");
                }
                if(question.getAnswerExplanation() == null || question.getAnswerExplanation().isEmpty()) {
                    throw new DataConflictException("Row " + index + ": " + "Answer Explanation is required");
                }
                if(question.getQuizType() == null) {
                    throw new DataConflictException("Row " + index + ": " + "Quiz Type is required");
                }

                questionList.add(question);
                for (Question q: mergedList) {
                    String savedTitle = q.getTitle().replaceAll("\\s+", "");
                    String newTitle = question.getTitle().replaceAll("\\s+", "");
                    if (savedTitle.equals(newTitle)) {
                        throw new DataConflictException("Row " + index + ": " + "Question is duplicated");
                    }
                }

                mergedList.add(question);
                index++;
            }

            workbook.close();
            for (Question newQuestion: questionList) {
                // set exQuiz for question
                exQuiz.getQuestions().add(newQuestion);
            }

            exQuizRepository.save(exQuiz);
            List<QuestionDto> questionDtos = questionList.stream().map(questionMapper::entityToDto).collect(Collectors.toList());
            return ResponseMapper.toDataResponseSuccess(questionDtos);
        } catch (Exception e) {
            throw new DataConflictException(e.getMessage());
        }
    }
}
