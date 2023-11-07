package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.Image;
import com.programming.courseservice.domain.persistent.entity.Language;
import com.programming.courseservice.domain.persistent.entity.Level;
import com.programming.courseservice.domain.persistent.entity.Topic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    private Set<ImageDto> images;
    private String authorName;
    private TopicDto topic;
}
