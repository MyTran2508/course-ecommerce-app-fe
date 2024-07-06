package com.programming.userservice.domain.persistent.enumrate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ActionName {

    GET("Lấy dữ liệu"),

    CREATE("Tạo dữ liệu"),

    UPDATE("Câp nhật dữ liệu"),

    DELETE("Xóa dữ liệu vĩnh viễn"),

    REMOVED("Xóa dữ liệu tạm thời"),

    LOGIN("Đăng nhập"),

    SIGNUP("Đăng ký"),

    RESET_PASSWORD("Đặt lại mật khẩu"),

    FORGET_PASSWORD("Quên mật khẩu"),

    ORDER("Đặt hàng"),

    APPROVE_COURSE("Chấp thuận khóa học"),

    DISAPPROVE_COURSE("Không chấp thuận khóa học"),

    APPROVAL_REQUEST("Yêu cầu xét duyệt khóa học");

    private final String value;
}
