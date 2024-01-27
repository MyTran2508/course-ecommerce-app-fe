package com.programming.courseservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum QuizType {
    SINGLE_CHOICE("Trắc nghiệm một đáp án"),

    MULTIPLE_CHOICE("Trắc nghiệm nhiều đáp án");

    private final String value;
}
