package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.enumrate.QuizType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer ordinalNumber;

    private String title;

    private String options;

    private String rightAnswer;

    private String answerExplanation;

    private QuizType quizType;

    public QuestionDto(String id) {
        this.id = id;
    }
}
