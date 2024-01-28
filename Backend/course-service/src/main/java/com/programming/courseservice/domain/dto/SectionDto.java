package com.programming.courseservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer ordinalNumber;

    private String name;

    private List<LectureDto> lectures;

    @JsonBackReference
    private ContentDto content;

    private Long totalDurationVideoLectures;
}
