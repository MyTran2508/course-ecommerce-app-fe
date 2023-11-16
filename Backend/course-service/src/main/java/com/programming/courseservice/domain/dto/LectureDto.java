package com.programming.courseservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureDto {
    private String id;
    private Integer ordinalNumber;
    private String name;
    private String url;
    private Long videoDuration;
}
