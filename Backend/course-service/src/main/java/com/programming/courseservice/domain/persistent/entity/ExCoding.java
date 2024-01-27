//package com.programming.courseservice.domain.persistent.entity;
//
//import com.main.progamming.common.model.BaseModel;
//import com.programming.courseservice.domain.persistent.enumrate.DifficultyType;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Table(
//        name = "ex_coding"
//)
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class ExCoding extends BaseModel {
//    private String title;
//
//    private String category;
//
//    private DifficultyType difficulty;
//
//    @Column(name = "problem_statement")
//    private String problemStatement;
//
//    private String examples;
//
//    @Column(name = "starter_code")
//    private String starterCode;
//
//    @Column(name = "handle_function")
//    private String handleFunction;
//
//    @OneToMany(mappedBy = "exCoding", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<ExCodingExample> exCodingExamples;
//
//    @Column(name = "url_video")
//    private String urlVideo;
//
//    private String link;
//}
