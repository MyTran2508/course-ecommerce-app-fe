package com.programming.userservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModuleName {

    USER("Người Dùng"),

    ROLE("Phân Quyền"),

    COURSE_MANAGER("Quản Lý Khóa Học Cấp Quản Lý"),

    COURSE_ADMIN("Quản Lý Khóa Học Cấp Admin"),

    ORDER("Đơn Hàng"),

    USER_LOG("Lịch Sử Người Dùng"),

    STATISTIC("Thống Kê");

    private final String value;
}
