package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.courseservice.domain.persistent.enumrate.QuizType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseModel {

    @Column(name = "ordinal_number")
    private Integer ordinalNumber;

    @Column(length = 256)
    private String title;

    @Column(length = 512)
    private String options;

    @Column(length = 16, name = "right_answer")
    private String rightAnswer;

    @Column(name = "answer_explanation")
    private String answerExplanation;

    @Column(name = "quiz_type")
    private QuizType quizType;
}
