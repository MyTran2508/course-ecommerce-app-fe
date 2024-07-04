package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.enumrate.DifficultyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ExQuizDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private DifficultyType difficulty;

    private String category;

    private Long limitTime;

    private Integer maxAttemptNumber;

    private Integer requiredScore;

    private List<QuestionDto> questions;

    private Integer totalQuestion;
}
