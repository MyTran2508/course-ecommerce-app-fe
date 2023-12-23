package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.Language;
import com.programming.courseservice.domain.persistent.entity.Level;
import com.programming.courseservice.domain.persistent.entity.Topic;
import com.programming.courseservice.domain.persistent.enumrate.FilterSortBy;
import com.programming.courseservice.domain.persistent.enumrate.Ratings;
import com.programming.courseservice.domain.persistent.enumrate.VideoDuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCourseDto {
    private List<String> levelIds;
    private List<String> languageIds;
    private List<String> topicIds;
    private Boolean isFree;
    private VideoDuration videoDuration;
    private Ratings ratings;
    private FilterSortBy filterSortBy;
    private String keyword;
    private int pageIndex;
    private int pageSize;
}
