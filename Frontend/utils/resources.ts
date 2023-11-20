import { User } from './../types/user.type';
export enum Action {
  SENT_OTP = 'SENT_OTP',
  REGISTER = 'REGISTER',
  FORGOT_PASSWORD = 'FORGOT_PASSWORD'
}

export enum StatusCode {
  REQUEST_SUCCESS = 200,
  DATA_NOT_FOUND = 404,
  DATA_NOT_MAP = 406,
  NOT_IMPLEMENTED = 501,
  DATA_CONFLICT = 409,
  NOT_PERMISSION = 403
}

export enum ToastStatus {
  SUCCESS = 'success',
  WARNING = 'warning',
  ERROR = 'error'
}

export enum ToastMessage {
  LOGIN_SUCCESS = 'Đăng Nhập Thành Công',
  LOGIN_FAIL = 'Đăng Nhập Thất Bại',
  LOGOUT_SUCCESS = "Đăng Xuất Thành Công",
  NOT_SELECT_PAYMENT = "Vui Lòng Chọn Phương Thức Thanh Toán",
  ADD_CART_SUCCESS = "Thêm Sản Phẩm Thành Công",
  ADD_CART_DUPLICATE = "Sản Phẩm Đã Có Trong Giỏ Hàng",
  DELETE_CART_SUCCESS = "Xóa Sản Phẩm Thành Công",
  CHANGE_PASSWORD_SUCCESS = "Đổi Mật Khẩu Thành Công",
  CHANGE_PASSWORD_FAIL = "Mật Khẩu Không Chính Xác",
  UPDATE_USER_SUCCESS = "Cập Nhật Thông Tin Thành Công",
  UPDATE_USER_FAIL = "Cập Nhật Thông Tin Thất Bại",
  CREATE_COURSE_SUCCESS = "Thêm Thông Tin Khóa Học Thành Công",
  CREATE_COURSE_FAIL = "Thêm Thông Tin Khóa Học Thất Bại",
  DATA_COURSE_EXISTED = "Tên Khóa Học Đã Tồn Tại",
  UPDATE_COURSE_SUCCESS = "Cập Nhật Thông Tin Khóa Học Thành Công",
  UPDATE_COURSE_FAIL = "Cập Nhật Thông Tin Khóa Học Thất Bại",
  UPDATE_CONTENT_SUCCESS = "Cập Nhật Thông Tin Chi Tiết Thành Công",
  UPDATE_CONTENT_FAIL = "Cập Nhật Thông Tin Chi Tiết Thất Bại",
  CHECK_CREATE_CONTENT = "Vui Lòng Hoàn Thành Chi Tiết Khóa Học",
  PAYMENT_SUCCESS = "Thanh Toán Thành Công",
  PAYMENT_FAIL = "Thanh Toán Thất Bại",
}

export enum Role {
  USER = "ROLE_USER",
  ADMIN = "ROLE_ADMIN",
  MANAGER = "ROLE_MANAGER"
}

export enum ApiResource {

} 

export enum CourseDescriptionField {
  REQUIREMENT = "REQUIREMENT",
  DETAIL = "DETAIL",
  TARGET_CONSUMER = "TARGET_CONSUMER"
}

export enum CourseSessionField {
  SESSION = "SESSION",
  LECTURE = "LECTURE",
}

export enum CourseLectureField {
  URL = "url",
  NAME = "name",
  FILENAME = "fileName"
}