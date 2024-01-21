package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_ex_quiz"
)
public class UserQuiz extends BaseModel {
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "count_answer_count", nullable = false)
    private Short correctAnswerCount;

    @Column(name = "start_time")
    private Long startTime;

    @OneToMany(targetEntity = UserAnswer.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_quiz_id", foreignKey = @ForeignKey(name = "fk_user_answer_user_quiz_id"))
    @OrderBy("question.ordinalNumber ASC")
    private List<UserAnswer> userAnswers;

    @OneToOne(targetEntity = ExQuiz.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ex_quiz_id", foreignKey = @ForeignKey(name = "fk_user_quiz_ex_quiz_id"))
    private ExQuiz exQuiz;
}
