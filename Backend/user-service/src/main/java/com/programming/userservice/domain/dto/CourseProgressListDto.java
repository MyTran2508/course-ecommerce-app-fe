package com.programming.userservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressListDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String userId;

    private List<String> courseId;
}
