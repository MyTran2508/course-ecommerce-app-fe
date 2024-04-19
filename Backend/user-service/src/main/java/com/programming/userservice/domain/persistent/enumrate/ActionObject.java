package com.programming.userservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionObject {
    USER("Tài khoản"),

    ORDER("Hóa đơn"),

    COURSE("Khóa học"),

    COURSE_REVIEWS("Đánh giá khóa học"),

    COURSE_PROGRESS("Tiến độ khóa học");

    private final String value;
}
