package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.domain.persistent.entity.Quiz;
import com.programming.courseservice.domain.persistent.enumrate.DifficultyType;
import com.programming.courseservice.domain.persistent.enumrate.QuizType;
import jakarta.persistence.*;
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
    private DifficultyType difficulty;

    private String category;

    private Long limitTime;

    private List<Quiz> quizList;
}
