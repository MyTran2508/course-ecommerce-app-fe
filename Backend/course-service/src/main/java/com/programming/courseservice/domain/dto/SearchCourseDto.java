package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.Language;
import com.programming.courseservice.domain.persistent.entity.Level;
import com.programming.courseservice.domain.persistent.entity.Topic;
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
//    private String key;
    private int pageIndex;
    private int pageSize;
}
