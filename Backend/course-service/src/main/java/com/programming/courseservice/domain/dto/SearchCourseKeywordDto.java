package com.programming.courseservice.domain.dto;

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
public class SearchCourseKeywordDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String keyword;

    private Integer keywordTypeSearchCourse;
}
