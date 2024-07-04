package com.programming.courseservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class UserAnswerDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String currentAnswer;

    private Boolean isCorrect;

    private QuestionDto question;

    @JsonBackReference
    private UserQuizDto userQuiz;
}
