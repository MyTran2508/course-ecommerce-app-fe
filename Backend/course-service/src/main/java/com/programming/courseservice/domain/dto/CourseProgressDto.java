package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgressDto {
    private String userId;

    private Integer currentProgress;

    private Integer totalAmountOfLecture;

    private Double rateProgress;

    private CourseDto course;
}
