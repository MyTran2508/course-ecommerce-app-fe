package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.courseservice.domain.persistent.enumrate.DifficultyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "ex_quiz"
)
public class ExQuiz extends BaseModel {
    @Column(name = "quiz_name", length = 64)
    private String quizName;

    @Column(length = 16)
    private DifficultyType difficulty;

    @Column(length = 64)
    private String category;

    @Column(name = "limit_time")
    private Long limitTime;

    @OneToMany(mappedBy = "exQuiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questionList;
}
