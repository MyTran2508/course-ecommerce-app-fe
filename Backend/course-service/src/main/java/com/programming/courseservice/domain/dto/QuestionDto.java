package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.enumrate.QuizType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    private String id;

    private Integer ordinalNumber;

    private String title;

    private String options;

    private String rightAnswer;

    private String answerExplanation;

    private QuizType quizType;
}
