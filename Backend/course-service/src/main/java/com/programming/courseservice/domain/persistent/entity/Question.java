package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.main.progamming.common.util.ExcludeFromComparisonField;
import com.programming.courseservice.domain.persistent.enumrate.QuizType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseModel implements Serializable {

    @Serial
    @ExcludeFromComparisonField
    private static final long serialVersionUID = 1L;

    @Column(name = "ordinal_number")
    private Integer ordinalNumber;

    @Column(length = 512)
    private String title;

    @Column(length = 3000)
    private String options;

    @Column(length = 16, name = "right_answer")
    private String rightAnswer;

    @Column(name = "answer_explanation", length = 5000)
    private String answerExplanation;

    @Column(name = "quiz_type")
    private QuizType quizType;
}
