package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.enumrate.FilterSortBy;
import com.programming.courseservice.domain.persistent.enumrate.RatingsLevel;
import com.programming.courseservice.domain.persistent.enumrate.VideoDuration;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchCourseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> levelIds;

    private List<String> languageIds;

    private List<String> topicIds;

    private Boolean isFree;

    private VideoDuration videoDuration;

    private RatingsLevel ratingsLevel;

    private FilterSortBy filterSortBy;

    private List<SearchCourseKeywordDto> searchCourseKeywordDtoList;

    private int pageIndex;

    private int pageSize;
}
