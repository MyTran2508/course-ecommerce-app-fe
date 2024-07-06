package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.courseservice.domain.persistent.enumrate.DifficultyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "ex_quiz"
)
public class ExQuiz extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(length = 16)
    private DifficultyType difficulty;

    @Column(length = 64)
    private String category;

    @Column(name = "limit_time")
    private Long limitTime;

    @Column(name = "max_attempt_number")
    private Integer maxAttemptNumber;

    @Column(name = "required_score")
    private Integer requiredScore;

    @OneToMany(targetEntity = Question.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_quiz_id", foreignKey = @ForeignKey(name = "fk_question_ex_quiz_id"))
    @OrderBy("ordinalNumber ASC")
    private List<Question> questions;
}
