package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.Question;
import com.programming.courseservice.domain.persistent.enumrate.DifficultyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ExQuizDto {
    private String id;

    private DifficultyType difficulty;

    private String category;

    private Long limitTime;

    private List<QuestionDto> questions;
}
