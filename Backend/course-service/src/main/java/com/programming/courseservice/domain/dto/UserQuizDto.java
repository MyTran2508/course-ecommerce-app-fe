package com.programming.courseservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserQuizDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private Long startTime;

    private Long limitTime;

    private String exQuizId;

    private Short correctAnswerCount;

    private Double score;

    private Integer attemptNumber;

    private Boolean isCompleted;

    @JsonManagedReference
    private List<UserAnswerDto> userAnswers;
}
