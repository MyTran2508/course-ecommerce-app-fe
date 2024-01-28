package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_ex_quiz"
)
@SuperBuilder(toBuilder = true)
public class UserQuiz extends BaseModel {
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @Column(name = "limit_time", nullable = false)
    private Long limitTime;

    @Column(name = "ex_quiz_id", nullable = false)
    private String exQuizId;

    @Column(name = "count_answer_count")
    private Short correctAnswerCount;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    private Double score;

    @OneToMany(mappedBy = "userQuiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("question.ordinalNumber ASC")
    private List<UserAnswer> userAnswers;

    @Override
    protected void ensureId() {
        super.ensureId();
        this.setIsCompleted(false);
    }
}
