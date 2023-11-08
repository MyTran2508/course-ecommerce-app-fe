package com.programming.courseservice.domain.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private String id;
    private String name;
    private String subTitle;
    private Double price;
    private LevelDto level;
    private LanguageDto language;
    private String urlCourseImages;
    private String urlPromotionVideos;
    private String authorName;
    private TopicDto topic;
    private Boolean isApproved;
}
