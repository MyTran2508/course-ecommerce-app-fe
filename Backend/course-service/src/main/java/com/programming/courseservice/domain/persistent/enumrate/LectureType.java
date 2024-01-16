package com.programming.courseservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LectureType {
    VIDEO("Video bài giảng"),

    DOCUMENT("Tài liệu"),

    EXERCISE_CODING("Bài tập lập trình tư duy"),

    EXERCISE_PRACTICAL("Bài tập thực hành"),

    QUIZ_TEST("Bài tập trắc nghiệm");

    private final String value;
}
