package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.courseservice.domain.persistent.enumrate.DifficultyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

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

    @OneToOne(mappedBy = "exQuiz", fetch = FetchType.LAZY)
    private Lecture lecture;

    @OneToMany(targetEntity = Question.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_quiz_id", foreignKey = @ForeignKey(name = "fk_question_ex_quiz_id"))
    private List<Question> questions;
}
