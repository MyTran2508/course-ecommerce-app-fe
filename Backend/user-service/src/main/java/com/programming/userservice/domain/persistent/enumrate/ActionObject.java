package com.programming.userservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionObject {

    USER("Tài khoản"),

    ROLE("Quyền"),

    ORDER("Hóa đơn"),

    COURSE("Khóa học"),

    COURSE_REVIEWS("Đánh giá khóa học"),

    COURSE_PROGRESS("Tiến độ khóa học"),

    SECTION("Chương khóa học"),

    CONTENT("Nội dung khoa học"),

    ASSIGNMENT("Bài tập tự luận"),

    LECTURE("Bài giảng"),

    EX_QUIZ("Bài kiểm tra trắc nghiệm"),

    COURSE_REVIEW("Đánh giá khóa học");

    private final String value;
}
