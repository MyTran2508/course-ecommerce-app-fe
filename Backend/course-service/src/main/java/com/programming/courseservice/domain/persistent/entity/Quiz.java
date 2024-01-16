package com.programming.courseservice.domain.persistent.entity;

import com.programming.courseservice.domain.persistent.enumrate.DifficultyType;
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
public class Quiz {
    @Id
    private String id;

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

    @PrePersist
    private void ensureId() {
        this.id = UUID.randomUUID().toString();
    }
}