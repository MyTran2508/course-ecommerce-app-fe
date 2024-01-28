package com.programming.courseservice.domain.persistent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_answer"
)
public class UserAnswer {

    @Id
    private String id;

    /**
     * currentAnswer >< rightAnswer
     */
    @Column(name = "current_answer")
    private String currentAnswer;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @OneToOne(targetEntity = Question.class)
    @JoinColumn(name = "question_id", foreignKey = @ForeignKey(name = "fk_user_answer_question_id"))
    private Question question;

    @ManyToOne(targetEntity = UserQuiz.class)
    @JoinColumn(name = "user_quiz_id", foreignKey = @ForeignKey(name = "fk_user_answer_user_quiz_id"))
    private UserQuiz userQuiz;

    @PrePersist
    private void ensureId() {
        this.id = UUID.randomUUID().toString();
    }
}
