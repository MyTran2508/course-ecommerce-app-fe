package com.programming.courseservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {
    private String id;
    private Integer ordinalNumber;
    private String name;
    private List<LectureDto> lectures;
    @JsonBackReference
    private ContentDto content;
    private Long totalDurationVideoLectures;
}
