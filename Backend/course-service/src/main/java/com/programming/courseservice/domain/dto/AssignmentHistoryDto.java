package com.programming.courseservice.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AssignmentHistoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;

    private String textAnswer;

    private String urlFileAnswer;

    private Long timeSubmit;

    private Float score;

    private String evaluation;

    private AssignmentDto assignment;
}
