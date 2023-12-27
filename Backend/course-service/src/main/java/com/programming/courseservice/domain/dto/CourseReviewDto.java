package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseReviewDto {
    private String id;

    private CourseDto courseDto;

    private String message;

    private Short rating;

    private String username;
}
