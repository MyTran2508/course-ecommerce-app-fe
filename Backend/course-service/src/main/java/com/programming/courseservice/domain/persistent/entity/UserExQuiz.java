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
public class UserExQuiz extends BaseModel {
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "count_answer_count", nullable = false)
    private Short correctAnswerCount;

    @OneToMany(targetEntity = UserWrongAnswer.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_ex_quiz_id", foreignKey = @ForeignKey(name = "fk_user_wrong_answer_user_ex_quiz_id"))
    private List<UserWrongAnswer> userWrongAnswerList;

    @OneToOne(targetEntity = ExQuiz.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ex_quiz_id", foreignKey = @ForeignKey(name = "fk_user_ex_quiz_ex_quiz_id"))
    private ExQuiz exQuiz;
}
