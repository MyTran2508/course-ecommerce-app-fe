package com.programming.courseservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DifficultyType {

    EASY("Dễ"),

    MEDIUM("Vừa"),

    HARD("Khó");

    private final String value;
}
