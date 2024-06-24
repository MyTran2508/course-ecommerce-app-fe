package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_answer"
)
public class UserAnswer extends BaseModel {

    /** currentAnswer >< rightAnswer */
    @Column(name = "current_answer")
    private String currentAnswer;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @ManyToOne(targetEntity = Question.class)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_user_answer_question_id"))
    private Question question;

    @ManyToOne(targetEntity = UserQuiz.class)
    @JoinColumn(name = "user_quiz_id", foreignKey = @ForeignKey(name = "fk_user_answer_user_quiz_id"))
    private UserQuiz userQuiz;

    @Column(name = "attempt_number")
    private Integer attemptNumber;
}
