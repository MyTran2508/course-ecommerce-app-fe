package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@Table(
        name = "course_progress"
)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class CourseProgress extends BaseModel {
    private String userId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_course_progress_course"))
    private Course course;

    @Column(name = "current_progress")
    private Integer currentProgress;

    @Column(name = "total_amount_lecture")
    private Integer totalAmountOfLecture;

    @Column(name = "rate_progress")
    private Double rateProgress;

    @Override
    protected void ensureId() {
        double rate = (double) currentProgress / totalAmountOfLecture;
        this.rateProgress = Math.round(rate * 100.0) / 100.0;
        super.ensureId();
    }

    @Override
    protected void setUpdated() {
        double rate = (double) currentProgress / totalAmountOfLecture;
        this.rateProgress = Math.round(rate * 100.0) / 100.0;
        super.setUpdated();
    }
}
