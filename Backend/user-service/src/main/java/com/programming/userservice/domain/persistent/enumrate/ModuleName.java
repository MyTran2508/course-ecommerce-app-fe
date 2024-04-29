package com.programming.userservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModuleName {

    USER("Người Dùng"),

    ROLE("Phân Quyền"),

    Course("Khóa Học"),

    COURSE_REVIEWS("Đánh giá khóa học"),

    Content("Nội Dung Khóa Học");

    private final String value;
}
