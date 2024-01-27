package com.programming.courseservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerDto {
    private String id;

    private String currentAnswer;

    private Boolean isCorrect;

    private QuestionDto question;
}
