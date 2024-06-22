package com.programming.courseservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CodingLanguage {

    PYTHON("Python"),

    JAVA("Java"),

    JAVASCRIPT("JavaScript"),

    C("C"),

    SQLite_3("SQLite 3");

    private final String value;
}
