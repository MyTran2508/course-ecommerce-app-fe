package com.programming.userservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ActionName {
    GET("Lấy dữ liệu"),
    CREATE("Tạo dữ liệu"),
    UPDATE("Câp nhật dữ liệu"),
    DELETE("Xóa dữ liệu"),
    LOGIN("Đăng nhập"),
    LOGOUT("Đăng xuất");

    private final String value;
}
