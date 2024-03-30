package com.programming.courseservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseReviewDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private CourseDto course;

    private String message;

    private Float rating;

    private String username;

    private String userAvatar;
}
