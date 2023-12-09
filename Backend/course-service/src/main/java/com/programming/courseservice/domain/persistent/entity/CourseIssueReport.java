package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.courseservice.domain.persistent.enumrate.IssueType;
import com.programming.courseservice.domain.persistent.enumrate.SeverityLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
public class CourseIssueReport extends BaseModel {
    @Column(name = "issue_type")
    private IssueType issueType;

    private String message;

    @Column(name = "severity_level")
    private String severityLevel;

    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_course_issue_reports_course"))
    @ToString.Exclude
    private Course course;
}
