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
    @Column(length = 16)
    private DifficultyType difficulty;

    @Column(length = 64)
    private String category;

    @Column(name = "limit_time")
    private Long limitTime;

    @OneToMany(targetEntity = Quiz.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_quiz_id", foreignKey = @ForeignKey(name = "fk_quiz_ex_quiz_id"))
    private List<Quiz> quizList;

    @ManyToOne(targetEntity = Lecture.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", foreignKey = @ForeignKey(name = "fk_ex_quiz_lecture_id"))
    private Lecture lecture;
}
