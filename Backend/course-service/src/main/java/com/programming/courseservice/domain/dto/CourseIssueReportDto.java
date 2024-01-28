package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.enumrate.IssueType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseIssueReportDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private IssueType issueType;

    private String message;

    // Value: NONE, LOW, MEDIUM, HIGH -> SeverityLevel
    private String severityLevel;
}
