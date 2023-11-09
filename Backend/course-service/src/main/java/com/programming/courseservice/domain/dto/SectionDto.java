package com.programming.courseservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.programming.courseservice.domain.persistent.entity.Content;
import com.programming.courseservice.domain.persistent.entity.Document;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {
    private String id;
    private String name;
    private List<LectureDto> lectures;
    private List<DocumentDto> documents;
    @JsonBackReference
    private ContentDto content;
}