package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import com.programming.courseservice.domain.persistent.entity.UserAnswer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserQuizDto {
    private String id;

    private String userId;

    private Short correctAnswerCount;

    private Long startTime;

    private List<UserAnswerDto> userAnswers;

    private ExQuiz exQuiz;
}
