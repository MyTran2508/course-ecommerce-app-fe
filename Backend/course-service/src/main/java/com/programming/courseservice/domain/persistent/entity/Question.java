package com.programming.courseservice.domain.persistent.entity;

import com.programming.courseservice.domain.persistent.enumrate.QuizType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
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

    @ManyToOne(targetEntity = ExQuiz.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_quiz_id", foreignKey = @ForeignKey(name = "fk_question_ex_quiz_id"))
    private ExQuiz exQuiz;
}
