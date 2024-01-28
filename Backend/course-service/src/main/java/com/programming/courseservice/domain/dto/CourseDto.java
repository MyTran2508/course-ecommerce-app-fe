package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.CourseIssueReport;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

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

    private Boolean isCompletedContent;

    private Boolean isAwaitingApproval;

    private List<CourseIssueReportDto> courseIssueReports;
}
