package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.main.progamming.common.util.ExcludeFromComparisonField;
import com.programming.courseservice.domain.persistent.enumrate.IssueType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        name = "course_issue_report"
)
@SuperBuilder(toBuilder = true)
@ToString
public class CourseIssueReport extends BaseModel implements Serializable {

    @Serial
    @ExcludeFromComparisonField
    private static final long serialVersionUID = 1L;

    @Column(name = "issue_type")
    private IssueType issueType;

    private String message;

    @Column(name = "severity_level")
    private String severityLevel;

    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_course_issue_reports_course"))
    @ToString.Exclude
    @ExcludeFromComparisonField
    private Course course;
}
