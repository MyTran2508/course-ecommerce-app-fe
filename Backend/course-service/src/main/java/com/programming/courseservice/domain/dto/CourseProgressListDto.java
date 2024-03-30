package com.programming.courseservice.domain.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressListDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;

    private List<String> courseId;
}
