package com.programming.courseservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IssueType {
    CONTENT_FORMAT("CONTENT FORMAT ERROR"),
    FILE_ERROR("IMAGE OR VIDEO FILE ERROR"),
    OTHER("OTHER TYPES OF ERRORS");
    private final String value;
}
