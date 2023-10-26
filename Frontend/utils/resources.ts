export enum Action {
  SENT_OTP = 'SENT_OTP',
  REGISTER = 'REGISTER'
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
  LOGIN_FAIL = 'Đăng Nhập Không Thành Công',
  LOGOUT_SUCCESS = 'Đăng Xuất Thành Công'
}