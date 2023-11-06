package com.programming.courseservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCourseDto {
    private List<String> levelId;
    private List<String> languageId;
    private boolean isFree;
}
