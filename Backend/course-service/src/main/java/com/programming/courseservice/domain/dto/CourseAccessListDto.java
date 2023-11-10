package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseAccessListDto {
    private String userId;
    private List<String> courseId;
}
