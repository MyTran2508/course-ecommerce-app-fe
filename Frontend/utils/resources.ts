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
  DELETE_CART_SUCCESS = "Xóa Sản Phẩm Thành Công",
  CHANGE_PASSWORD_SUCCESS = "Đổi Mật Khẩu Thành Công",
  CHANGE_PASSWORD_FAIL = "Mật Khẩu Không Chính Xác",
  UPDATE_USER_SUCCESS = "Cập nhật thông tin thành công",
  UPDATE_USER_FAIL = "Cập nhật thông tin thất bại",
}

export enum Role {
  USER = "USER",
  ADMIN = "ADMIN",
  MANAGER = "MANAGER"
}

export enum ApiResource {

} 