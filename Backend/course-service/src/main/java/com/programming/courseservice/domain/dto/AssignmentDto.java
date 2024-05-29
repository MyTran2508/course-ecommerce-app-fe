package com.programming.courseservice.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AssignmentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String question;

    private String urlVideoSolution;

    private String urlFileSolution;

    private Long estimatedDuration;

    private String urlVideoInstructions;

    private String textInstructions;

    private String urlFileResource;
}
