package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.enumrate.IssueType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseIssueReportDto {
    private String id;

    private IssueType issueType;

    private String message;

    // Value: NONE, LOW, MEDIUM, HIGH -> SeverityLevel
    private String severityLevel;
}
