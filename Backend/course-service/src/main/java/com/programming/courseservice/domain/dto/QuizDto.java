package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.domain.persistent.enumrate.QuizType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private String id;

    private Integer ordinalNumber;

    private String title;

    private String option;

    private String rightAnswer;

    private QuizType quizType;

    private String answerExplanation;
}
