package com.programming.courseservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AssignmentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String questions;

    private String urlVideoInstructions;

    private String textInstructions;

    private String urlFileResource;

    private Long estimatedDuration;

    private String urlVideoSolution;

    private String urlFileSolution;

    private String textSolution;

    @JsonBackReference
    private LectureDto lecture;
}
