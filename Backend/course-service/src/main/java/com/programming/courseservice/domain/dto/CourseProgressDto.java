package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.Course;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseProgressDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;

    private Integer currentProgress;

    private Integer totalAmountOfLecture;

    private Double rateProgress;

    private CourseDto course;
}
