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

    @NotNull(message = "Issue Type is not null")
    private IssueType issueType;

    @Size(min = 2, message = "Message must be greater than 4 characters.")
    @Size(max = 254, message = "Message must be less than 254 characters.")
    private String message;

    // Value: NONE, LOW, MEDIUM, HIGH -> SeverityLevel
    @NotNull(message = "Severity Level is not null")
    private String severityLevel;
}
